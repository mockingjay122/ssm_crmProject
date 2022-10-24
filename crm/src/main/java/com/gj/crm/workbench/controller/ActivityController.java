package com.gj.crm.workbench.controller;

import com.github.pagehelper.PageInfo;
import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.domian.ReturnObject;
import com.gj.crm.commons.utils.DataUtils;
import com.gj.crm.commons.utils.HssfUtils;
import com.gj.crm.commons.utils.StringUtils;
import com.gj.crm.commons.utils.UUIDUtils;
import com.gj.crm.settings.entity.User;
import com.gj.crm.settings.service.userService;
import com.gj.crm.workbench.entity.Activity;
import com.gj.crm.workbench.entity.ActivityRemark;
import com.gj.crm.workbench.service.ActivityRemarkService;
import com.gj.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author 郭嘉
 * @date 2022/9/30 - 17:50
 */
@Controller
public class ActivityController {
    @Autowired
    private userService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(Model model, HttpServletRequest request) {
        //调用service方法
        List<User> userList = userService.queryAllUsers();
        //把数据保存到request里
        request.setAttribute("userList", userList);

        Map<String, Object> map = new HashMap<>();
        map.put("name", Contants.NULL_VALUE);
        map.put("owner", Contants.NULL_VALUE);
        map.put("startDate", Contants.NULL_VALUE);
        map.put("endDate", Contants.NULL_VALUE);
        PageInfo<Activity> pageInfo = activityService.queryActivityByConditionForPage(map, Contants.INIT_PAGE_NO, Contants.INIT_PAGE_SIZE);
        model.addAttribute("page", pageInfo);
        return "workbench/activity/index";
    }


    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @Transactional(propagation = Propagation.REQUIRED)
    public @ResponseBody
    Object saveCreateActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SSESSION_USER_KEY);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DataUtils.formateDataTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        //调用service方法保存市场活动
        try {
            int ret = activityService.saveActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。");
        }
        return returnObject;
    }

    

    @RequestMapping("/workbench/activity/{pageNo}/{pageSize}")
    public String getActivityByConditionForPage(Model model, String name, String owner, String startDate, String endDate, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, HttpSession session) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        PageInfo<Activity> pageInfo = activityService.queryActivityByConditionForPage(map, pageNo, pageSize);
        model.addAttribute("page", pageInfo);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody
    Object deleteActivityByIds(String[] ids) {
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service方法

            int ret = activityService.deleteActivityByIds(StringUtils.buildIds(ids[0]));

            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重置");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/selectActivityById.do")
    public @ResponseBody
    Object selectActivityById(String ids) {
        //调用service
        Activity activity = activityService.selectActivityById(ids);
        //根据查询结果返回响应信息
        return activity;
    }

    @RequestMapping("/workbench/activity/updateActivity.do")
    public @ResponseBody Object updateActivity(Activity activity, HttpSession session) {
        //封装参数
        User user = (User) session.getAttribute(Contants.SSESSION_USER_KEY);
        activity.setEditTime(DataUtils.formateDataTime(new Date()));
        activity.setEditBy(user.getId());

        ReturnObject returnObject = new ReturnObject();
        try {
            int res = activityService.updateActivity(activity);
            if (res > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重置");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setMessage("系统忙，请稍后重置");
        }
        return  returnObject;
    }
   /* @RequestMapping("/workbench/activity/download.do")
    public void fileDownload(HttpServletResponse response) throws Exception {
        //设置响应信息
        response.setContentType("application/octet-stream;charset=UTF-8");
        // 获取输出流
        OutputStream os =response.getOutputStream();
        //设置响应头信息，是浏览器收到响信息后直接激活下载窗口
        response.addHeader("Content-Disposition","attachment;filename=myStudent.xls");
        // 读取excel文件，把输出到浏览器
        InputStream is=new FileInputStream("D:\\test\\crm-poi\\studentList.xls");
        byte[] buf=new byte[256];
        int len=0;
        while((len=is.read(buf))!=-1){
            os.write(buf,0,len);
        }
        is.close();
        os.flush();
    }*/
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletResponse response) throws Exception{
        //调用service方法
        List<Activity> activityList=activityService.selectAllActivity();
        //调用插件(创建excel文件)
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet =wb.createSheet("市场功能列表");
        HSSFRow row =sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改日期");
        cell=row.createCell(10);
        cell.setCellValue("修改者");
        //遍历list，每次遍历创建row对象
        //判断是否为空
        if(activityList!=null&&activityList.size()>0){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                //每遍历一个创建一行
                row =sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
        }

        }
        //根据wb对象生成excel文件
        /*OutputStream os=new FileOutputStream("D:\\test\\crm-sevice-activity\\activityList.xls");
        wb.write(os);*/
        //关闭资源
       /* os.close();
        wb.close();*/
        //把生成的文件下载到客户端
        //设置响应信息
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头信息，是浏览器收到响信息后直接激活下载窗口
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        // 获取输出流
        OutputStream osp =response.getOutputStream();
        /* InputStream is=new FileInputStream("D:\\test\\crm-sevice-activity\\activityList.xls");
        byte[] buff=new byte[256];
        int len=0;
        while((len=is.read(buff))!=-1){
            osp.write(buff,0,len);
        }*/
        wb.write(osp);
        wb.close();
        osp.flush();
    }
    @RequestMapping("/workbench/activity/exportAllActivityById.do/{ids}")
    public void exportAllActivityById(HttpServletResponse response,@PathVariable("ids") String[] ids) throws Exception{
        String [] finIds=StringUtils.buildIds(ids[0]);
        //调用service方法
        List<Activity> activityList=activityService.selectAllActivityById(finIds);
        //调用插件(创建excel文件)
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet =wb.createSheet("市场功能列表");
        HSSFRow row =sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改日期");
        cell=row.createCell(10);
        cell.setCellValue("修改者");
        //遍历list，每次遍历创建row对象
        //判断是否为空
        if(activityList!=null&&activityList.size()>0){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                //每遍历一个创建一行
                row =sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }

        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头信息，是浏览器收到响信息后直接激活下载窗口
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        // 获取输出流
        OutputStream osp =response.getOutputStream();
        wb.write(osp);
        wb.close();
        osp.flush();
    }
    /*@RequestMapping("/workbench/activity/fileUpLoad.do")
    public String fileUpLoad(MultipartFile myFile)throws Exception{
        //在服务器指定目录下生成同样的文件
        String filename=myFile.getOriginalFilename();
        File file=new File("D:\\test\\crm-sevice-activity\\"+filename);//路径必须手动创建好
        myFile.transferTo(file);
        //返回响应信息
        return "/";
    }*/
    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session) {
        //把接受到的excel文件写道磁盘上
        //在服务器指定目录下生成同样的文件
        //封装参数
        User user = (User) session.getAttribute(Contants.SSESSION_USER_KEY);
        ReturnObject returnObject=new ReturnObject();
        try {

            /*String filename=activityFile.getOriginalFilename();
            File file=new File("D:\\test\\crm-sevice-activity-import\\"+filename);//路径必须手动创建好
            activityFile.transferTo(file);*/
            //解析excel文件
            //InputStream is=new FileInputStream("D:\\test\\crm-sevice-activity-import\\"+filename);
            InputStream is=activityFile.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            HSSFSheet sheet=wb.getSheetAt(0);
            HSSFRow row=null;
            HSSFCell cell=null;
            Activity activity=null;
            List<Activity> list=new ArrayList<>();
            for(int i=1;i<=sheet.getLastRowNum();i++){
                row=sheet.getRow(i);
                activity=new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getName());
                activity.setCreateTime(DataUtils.formateDataTime(new Date()));
                activity.setCreateBy(user.getId());
                for(int j=0;j<row.getLastCellNum();j++){
                    cell=row.getCell(j);
                    String cellValue= HssfUtils.getCellValue(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                }
                list.add(activity);
            }
                int res=activityService.saveCreateActivity(list);
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(res);
            } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("导入失败，请重试");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/activity/detailActivity.do/{id}")
    public String detailActivity(@PathVariable("id") String id,Model model){
        //调用service
        Activity activity=activityService.selectForDetailById(id);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailById(activity.getId());
        //把数据保存到作用域中
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",activityRemarkList);
        //跳转页面
        return "workbench/activity/detail";
    }
}