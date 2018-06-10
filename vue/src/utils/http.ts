import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'


const TIMEOUT = 30000

const _client: any = axios.create({
    timeout: TIMEOUT,
})


export function del<T>(url: string, config?: AxiosRequestConfig): IResponse<T> {
    return _client.delete(url, config)
}


export function get<T>(url: string, config?: AxiosRequestConfig): IResponse<T> {
    return _client.get(url, config)
}


export function post<T>(url: string, data?: any, config?: AxiosRequestConfig): IResponse<T> {
    return _client.post(url, data, config)
}


export function put<T>(url: string, data?: any, config?: AxiosRequestConfig): IResponse<T> {
    return _client.put(url, data, config)
}


export interface IResponse<T> extends AxiosResponse {
    data: T
}
