export function getBaseUrl() {
    return process.env.NODE_ENV === 'production' || process.env.NODE_ENV === 'staging' ? process.env.VUE_APP_BASEURL : process.env.VUE_APP_BASEURL
}
