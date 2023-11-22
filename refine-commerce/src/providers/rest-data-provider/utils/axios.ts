/** @format */

import { HttpError } from '@refinedev/core'
import axios from 'axios'
// import { AppError } from 'types/error'
import { keycloak } from '../../..'

const axiosInstance = axios.create()

// axiosInstance.interceptors.request.use((req) => {
//   if (req.headers && keycloak.token)
//     req.headers.Authorization = `Bearer ${keycloak.token}`
//   return req
// })

axiosInstance.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    const customError: Error = {
      ...error,
      message: error.response?.data?.message,
      statusCode: error.response?.status,
    }

    return Promise.reject(customError)
  }
)

export { axiosInstance }
