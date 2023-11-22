import { useLogout } from "@refinedev/core"
import { useEffect } from "react"

const Logout = () => {
  const { mutate } = useLogout()

  useEffect(() => {
    mutate()
  }, [mutate])

  return (
    <></>
  )
}
export default Logout