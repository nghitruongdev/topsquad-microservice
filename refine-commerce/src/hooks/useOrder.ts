import { useCreate, useGetIdentity } from "@refinedev/core"
import { useNavigate } from "react-router-dom"
import CartContext from "../contexts/cart-context"
import { useKeycloak } from "@react-keycloak/web"

const useOrder = () => {
  const { data: user } = useGetIdentity<{ name: string }>()
  const navigate = useNavigate()
  const { cart, clearCart } = CartContext.useContext()
  const { mutate } = useCreate()
  const { keycloak } = useKeycloak()

  const checkout = () => {
    if (!user) {
      return navigate('/login')
    }

    if(!cart.length){
      return alert('No item in cart. Please add new product before checkout!')
    }
    
    const order = {
      userId: user.name,
      items: cart.map(({ id, price, amount }) => ({
        skuCode: id,
        price: price,
        quantity: amount
      }))
    }

    mutate({
      resource: 'orders',
      values: order,
       meta: {
      headers: {
        Authorization: `Bearer ${keycloak.token}`,
      }
    }
    }, {
      onSuccess() {
          clearCart()
          // alert('New order placed successfully')
      },
    })
  }


  return {
    checkout
  }
}

export default useOrder