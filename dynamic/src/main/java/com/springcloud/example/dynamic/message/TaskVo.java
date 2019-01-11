package com.springcloud.example.dynamic.message;

import lombok.Data;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/1/11 10:03
 */
@Data
public class TaskVo {
    private Byte order;// 序号0-12
    private Long inScreenBusinessId;// 梯内商业id
    private Integer inScreenBusinessNumber;// 梯内商业名称
    private Long inScreenResidenceId;// 梯内住宅id
    private Integer inScreenResidenceNumber;// 梯内住宅名称
    private Long outScreenBusinessId;// 梯外商业id
    private Integer outScreenBusinessNumber;// 梯外商业名称
    private Long outScreenResidenceId;// 梯外住宅id
    private Integer outScreenResidenceNumber;// 梯外住宅名称
    private Long inFrameBusinessId;// 梯内框商业id
    private Integer inFrameBusinessNumber;// 梯内框商业名称
    private Long inFrameResidenceId;// 梯内框住宅id
    private Integer inFrameResidenceNumber;// 梯内框住宅名称
}
