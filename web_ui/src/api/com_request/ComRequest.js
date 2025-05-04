import adminRequest from "@/utils/AdminRequest";

/**
 * 查询动态字典
 * @param {string} code
 * @returns
 */
export function queryComQueryByCode(code) {
    return adminRequest({
        url: `/common/query-com-query/${code}`,
        method: 'GET'
    })
}

/**
 * 查询静态字典
 * @param {string} dictType
 * @returns
 */
export function queryDictByType(dictType) {
    return adminRequest({
        url: `/common/query-dict/${dictType}`,
        method: 'GET'
    })
}
