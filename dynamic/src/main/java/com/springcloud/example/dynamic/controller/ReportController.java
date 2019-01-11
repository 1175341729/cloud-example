package com.springcloud.example.dynamic.controller;

import com.google.common.collect.Lists;
import com.springcloud.example.common.enums.ProgressTypeEnum;
import com.springcloud.example.common.enums.ScreenTypeEunm;
import com.springcloud.example.common.enums.TaskTypeEnum;
import com.springcloud.example.dynamic.dao.TaskMapper;
import com.springcloud.example.dynamic.message.TaskBaseReq;
import com.springcloud.example.dynamic.message.TaskDetailReq;
import com.springcloud.example.dynamic.message.TaskVo;
import com.springcloud.example.dynamic.model.Task;
import com.springcloud.example.dynamic.model.TaskExample;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/1/11 10:03
 */
@RestController
@RequestMapping("/report")
@Validated
public class ReportController {
    @Resource
    private TaskMapper taskMapper;

    @GetMapping("/task/company")
    public List<TaskVo> listTaskCompany(@Valid @NotNull(message = "所属年份不能为空！") @Min(value = 1,message = "年份必须为正整数！") Short year,
                                        @Valid @NotBlank(message = "公司不能为空！")String company,
                                        @Valid @Min(value = 1,message = "月必须为正整数！")Byte month){
        List<TaskVo> taskVoList = Lists.newArrayList();
        TaskVo taskVo;
        Task task;
        TaskExample example = new TaskExample();
        TaskExample.Criteria criteria = example.createCriteria();
        criteria.andYearEqualTo(year);
        criteria.andCompanyEqualTo(company);
        if (month == null){
            criteria.andWeekEqualTo((byte) 0);
        } else {
            criteria.andMonthEqualTo(month);
        }
        // 进度类型
        Byte progressType = ProgressTypeEnum.NEW_SIGN.getCode();
        criteria.andProgressTypeEqualTo(progressType);
        // 任务类型
        Byte taskType = TaskTypeEnum.DEVELOP_SIGN.getCode();
        criteria.andTaskTypeEqualTo(taskType);
        List<Task> tasks = taskMapper.selectByExample(example);
        Map<Byte, Map<String, Task>> taskMonth;
        byte order;
        if (month == null){
            order = 12;
            taskMonth = tasks.stream()
                    .collect(Collectors.groupingBy(Task::getMonth,
                            Collectors.toMap(vo -> vo.getProgressType() + "_" +vo.getTaskType() + "_" + vo.getScreenType(),
                                    vo -> vo)));
        } else { // TODO 周任务
            taskMonth = tasks.stream()
                    .collect(Collectors.groupingBy(Task::getWeek,
                            Collectors.toMap(vo -> vo.getProgressType() + "_" +vo.getTaskType() + "_" + vo.getScreenType(),
                                    vo -> vo)));
            order = 4;
        }
        for(byte i = 0;i <= order;i++){
            taskVo = new TaskVo();
            taskVo.setOrder(i);
            taskVoList.add(taskVo);
            Map<String, Task> map = taskMonth.get(i);
            if (CollectionUtils.isEmpty(map)) continue;
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.BUSINESS_IN_SCREEN.getCode());
            if (task != null){
                taskVo.setInScreenBusinessId(task.getId());
                taskVo.setInScreenBusinessNumber(task.getTargetNum());
            }
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.RESIDENCE_IN_SCREEN.getCode());
            if (task != null){
                taskVo.setInScreenResidenceId(task.getId());
                taskVo.setInScreenResidenceNumber(task.getTargetNum());
            }
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.BUSINESS_OUT_SCREEN.getCode());
            if (task != null){
                taskVo.setOutScreenBusinessId(task.getId());
                taskVo.setOutScreenBusinessNumber(task.getTargetNum());
            }
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.RESIDENCE_OUT_SCREEN.getCode());
            if (task != null){
                taskVo.setOutScreenResidenceId(task.getId());
                taskVo.setOutScreenResidenceNumber(task.getTargetNum());
            }
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.BUSINESS_IN_FRAME.getCode());
            if (task != null){
                taskVo.setInFrameBusinessId(task.getId());
                taskVo.setInFrameBusinessNumber(task.getTargetNum());
            }
            task = map.get(progressType + "_" + taskType + "_" + ScreenTypeEunm.RESIDENCE_IN_FRAME.getCode());
            if (task != null){
                taskVo.setInFrameResidenceId(task.getId());
                taskVo.setInFrameResidenceNumber(task.getTargetNum());
            }
        }
        return taskVoList;
    }

    @PostMapping("/task/company")
    @Transactional
    public Integer saveTask(@Valid @RequestBody TaskBaseReq taskBaseReq){
        List<TaskDetailReq> detailList = taskBaseReq.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)){
            Short year = taskBaseReq.getYear();// 年
            String company = taskBaseReq.getCompany();// 公司
            Byte progressType = ProgressTypeEnum.NEW_SIGN.getCode();// TODO 集团任务默认新签
            Byte TaskType = TaskTypeEnum.DEVELOP_SIGN.getCode();// TODO 到时通过接口区分
            // TODO 验证月目标相加 >= 年目标
            List<TaskDetailReq> addTask = detailList.stream().filter(vo -> vo.getId() == null).collect(Collectors.toList());
            Map<Long, Integer> modifyMap = detailList.stream().filter(vo -> vo.getId() != null).collect(Collectors.toMap(TaskDetailReq::getId, TaskDetailReq::getTargetNum));
            // 新增数据
            addTask.forEach(vo -> {
                Task task = new Task();
                task.setCompany(company);
                task.setProgressType(progressType);
                task.setTaskType(TaskType);
                task.setScreenType(vo.getScreenType());
                task.setYear(year);
                task.setMonth(vo.getOrder());
                task.setWeek((byte) 0);
                task.setTargetNum(vo.getTargetNum());
                task.setCreatorId(11);
                task.setCreateTime(new Date());
                task.setUpdateTime(new Date());
                taskMapper.insertSelective(task);
            });

            modifyMap.forEach((id,targetNumber) -> {
                Task task = new Task();
                task.setId(id);
                task.setTargetNum(targetNumber);
                taskMapper.updateByPrimaryKeySelective(task);
            });
        }
        return 1;
    }
}
