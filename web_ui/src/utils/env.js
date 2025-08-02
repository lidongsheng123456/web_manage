export function getBaseUrl() {
    return import.meta.env.MODE === 'production' || import.meta.env.MODE === 'staging' ? import.meta.env.VUE_APP_BASEURL : import.meta.env.VUE_APP_BASEURL
}

// 回显数据字典
export function selectDictLabel(datas, value) {
    if (value === undefined) {
        return "";
    }
    const actions = [];
    Object.keys(datas).some((key) => {
        if (datas[key].dictValue === value) {
            actions.push(datas[key].dictLabel);
            return true;
        }
    })
    if (actions.length === 0) {
        actions.push(value);
    }
    return actions.join('');
}

// 回显标签类型
export function selectTagType(datas, value) {
    if (value === undefined) {
        return "info";
    }
    const actions = [];
    Object.keys(datas).some((key) => {
        if (datas[key].dictValue === value) {
            actions.push(datas[key].tagType);
            return true;
        }
    })
    if (actions.length === 0) {
        actions.push('info');
    }
    return actions.join('');
}
