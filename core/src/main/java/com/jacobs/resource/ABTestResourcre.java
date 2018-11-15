package com.jacobs.resource;

import com.jacobs.module.User;

/**
 * @author lichao
 * @date 2018/11/12
 */
public class ABTestResourcre {

    /**
     * @api {POST} v1/metrics 上报指标接口
     * @apiGroup metrics
     * @apiVersion 1.0.0
     * @apiDescription 提供给计算平台上报可计算指标的接口
     *
     * @apiParam {String} product_name 产品线/业务线名称
     * @apiParam {jsonArray} metrics 具体可计算的指标
     *
     * @apiParamExample {json} Request-Example:
     [{
         "bussiness_name": "feed",
         "metrics": [{
                 "id": 10001,
                 "first_class": "一级分类",
                 "second_class": "二级分类",
                 "name": "avg_exposure",
                 "desc": ""
             },
             {
                 "id": 10002,
                 "first_class": "一级分类",
                 "second_class": "二级分类",
                 "name": "avg_refresh",
                 "desc": ""
             }
         ]
     }]
     *
     * @apiSuccess (200) {int} code 0 代表无错误 http code+bussiness code代表有错误
     * @apiSuccess (200) {String} msg 失败的话会有详细错误信息
     * @apiSuccessExample {json} 返回样例:
     * HTTP/1.1 200 Success
     * {
     *   "code": 0,
     *   "msg": "",
     * }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       code: “数字， 待定义”,
     *       "msg": "uri_not_found",
     *     }
     */

    /**
     * @api {POST} v1/tasks 下发具体的计算任务列表
     * @apiGroup metrics
     * @apiVersion 1.0.0
     * @apiDescription 实验开始或者变更时下发给计算平台具体任务，需要计算平台提供具体的接口实现
     *
     * @apiParam {Number} task_id 任务id, 由指标汇总模块统一生成
     * @apiParam {jsonArray} metrics_ids 要计算的指标的id列表
     * @apiParam {String} log_type 计算取划分日志的方式类型：raw：直接拿原始日志，rule：根据规则计算
     * @apiParam {jsonArray} buckets 实验详细分桶规则
     * @apiParam {jsonArray} calculate_time 需要计算的时间范围（按天）统一format成 "yyyy-MM-dd HH:mm:ss"格式
     *
     * @apiParamExample {json} Request-Example:
     [{
                "task_id": 1000,
                "metric_ids": [10001, 10002],
                "log_type": "raw|rule",
                "buckets": [
                    {
                        "name": "abc-1"
                    },
                    {
                        "name": "abc-1",
                        //详细划分规则
                        "condition": {
                            "id_partition": "crc32|3309178791548649482|100|1-10", //hash_method|hash_id|partitoin_total|partition
                            //用户条件选择，列表里面代表的是include，默认空列表为没有约束，
                            "user_groups": [{
                                "age": [14， 20],
                                "frequent": [2], //1:未使用，2:低频，3:中低频，4:中低频，5:高频，6:全勤
                                "city": [
                                    "北京",
                                    "上海"
                                ],
                                "level": ["c1", "f0"],//用户等级
                            }]
                        }
                    }
                ],
                // 按天: 2018-11-06 00:00:00, 按小时：2018-11-06 12:00:00, 按分钟...
                "calculate_time": ["2018-11-06 00:00:00", "2018-11-10 00:00:00"]
     }]
     *
     * @apiSuccess (200) {int} code 0 代表无错误 http code+bussiness code代表有错误
     * @apiSuccess (200) {String} msg 失败的话会有详细错误信息
     * @apiSuccessExample {json} 返回样例:
     * HTTP/1.1 200 Success
     * {
     *   "code": 0,
     *   "msg": "",
     * }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       code: “数字， 待定义”,
     *       "msg": "uri_not_found",
     *     }
     */

    /**
     * @api {POST} v1/task/:task_id/results 导出数据结果
     * @apiGroup metrics
     * @apiVersion 1.0.0
     * @apiDescription 提供给计算平台导出计算结果接口
     *
     * @apiParam {Number} task_id 计算任务id
     * @apiParam {Number} metric_id 计算的指标id
     * @apiParam {String} calculate_time 计算的时间
     * @apiParam {String} bucket_name 具体的分桶的组名
     * @apiParam {boolean} is_summary 是否是汇总指标
     * @apiParam {json} data 指标对应的详细值
     *
     * @apiParamExample {json} Request-Example:
     {
         "task_id": 1000,
         "metric_id": 10001,
         "calculate_time": ["2018-11-06 00:00:00"], //如果是is_summary，calculate_time就应该是一个range了
         "bucket_name": "abc-1",
         "is_summary": true,
         "data": {
             "value": 10, //指标值
             "mean": 10, //均值
             "sample": 1000, //样本量
             "standard_deviation": 0.00, //标准差
         }
     }
     *
     * @apiSuccess (200) {int} code 0 代表无错误 http code+bussiness code代表有错误
     * @apiSuccess (200) {String} msg 失败的话会有详细错误信息
     * @apiSuccessExample {json} 返回样例:
     * HTTP/1.1 200 Success
     * {
     *   "code": 0,
     *   "msg": "",
     * }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       code: “数字， 待定义”,
     *       "msg": "uri_not_found",
     *     }
     */
}
