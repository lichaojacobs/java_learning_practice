package com.jacobs.resource;

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
     *
     * @apiSuccess (200) {int} code 0 代表无错误 http code+bussiness code代表有错误
     * @apiSuccess (200) {String} msg 失败的话会有详细错误信息
     * @apiSuccessExample {json} 返回样例:
     * HTTP/1.1 200 Success
     {
         "code": 0,
         "data": [{
             "businessName": "feed",
             "metrics": [{
                     "id": "10001",
                     "firstClass": "一级分类",
                     "secondClass": "二级分类",
                     "thirdClass": "三级分类",
                     "name": "avg_exposure",
                     "valueType": "",
                     "desc": "",
                     "weight": ""
                 },
                 {
                     "id": "10002",
                     "firstClass": "一级分类",
                     "secondClass": "二级分类",
                     "thirdClass": "三级分类",
                     "name": "avg_refresh",
                     "valueType": "",
                     "desc": "",
                     "weight": ""
                 }
             ]
         }]
     }
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
     * @apiParam {Number} taskId 任务id, 由指标汇总模块统一生成
     * @apiParam {String} displayName 实验名称
     * @apiParam {jsonArray} metricIds 要计算的指标的id列表
     * @apiParam {String} logType 计算取划分日志的方式类型：raw：直接拿原始日志，rule：根据规则计算
     * @apiParam {jsonArray} buckets 实验详细分桶规则
     * @apiParam {jsonArray} startTime 时间范围，开始时间（按天）统一format成 "yyyy-MM-dd HH:mm:ss"格式
     * @apiParam {jsonArray} endTime 时间范围，结束时间（按天）统一format成 "yyyy-MM-dd HH:mm:ss"格式
     *
     * @apiParamExample {json} Request-Example:
     [{
         "taskId": 1000,
         "displayName": "实验一",
         "metricIds": ["10001", "10002"],
         "logType": "raw|rule",
         "buckets": [{
                 "name": "abc-1"
             },
             {
                 "name": "abc-1",
                 //详细划分规则
                 "condition": {
                     "idPartition": "crc32|3309178791548649482|100|1-10", //hash_method|hash_id|partitoin_total|partition
                     //用户条件选择，列表里面代表的是include，默认空列表为没有约束，
                     "userGroups": {
                         "level": ["C1", "F0"], //层级
                         "frequent": [1, 2], //1: 低频 2:中低频 3: 中高频 4:高频 5: 全勤  6: 新户
                         "followNum": ["100-1000", "2000-5000"], // 关注数
                         "fansNum": ["100-1000", "2000-5000"], // 粉丝数
                     }
                 }
             }
         ],
         // 按天: 2018-11-06 00:00:00, 按小时：2018-11-06 12:00:00, 按分钟...
         "startTime": "2018-11-06 00:00:00",
         "endTime": "2018-11-06 00:00:00"
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
     * @api {POST} /api-calculate/v1/tasks/:task_id/results 导出数据结果
     * @apiGroup metrics
     * @apiVersion 1.0.0
     * @apiDescription 提供给计算平台导出计算结果接口
     *
     * @apiParam {Number} taskId 计算任务id
     * @apiParam {Number} metricId 计算的指标id
     * @apiParam {String} startTime 计算的时间
     * @apiParam {String} endTime 计算的结束时间
     * @apiParam {String} bucketName 具体的分桶的组名
     * @apiParam {json} data 指标对应的详细值
     *
     * @apiParamExample {json} Request-Example:
     [{
         "taskId": 1000,
         "metricId": "10001",
         "startTime": "2018-11-06 00:00:00",
         "endTime": "2018-11-06 00:00:00",
         "bucketName": "abc-1",
         "data": {
             "value": 10, //指标值
             "sample": 1000, //样本量
             "standardDeviation": 0.00, //标准差
         }
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
}
