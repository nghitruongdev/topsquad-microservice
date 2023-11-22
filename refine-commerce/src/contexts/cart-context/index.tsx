/* eslint-disable @typescript-eslint/no-empty-function */
import React, { PropsWithChildren, useEffect, useState } from "react"
import { ICartItem, IProduct } from "../../types";



interface CartContextProps {
  cart: ICartItem[];
  addToCart: (product: IProduct, id: string) => void;
  removeFromCart: (id: string) => void;
  clearCart: () => void;
  increaseAmount: (id: string) => void;
  decreaseAmount: (id: string) => void;
  itemAmount: number;
  total: number;
}

const Context = React.createContext<CartContextProps | null>(null);



const CartProvider: React.FC<PropsWithChildren> = ({ children }) => {
  const [cart, setCart] = useState<ICartItem[]>([]);
  const [itemAmount, setItemAmount] = useState(0);
  const [total, setTotal] = useState(0);

  useEffect(() => {
    const calculateTotal = () => {
      const newTotal = cart.reduce((accumulator, currentItem) => {
        return accumulator + currentItem.price * currentItem.amount;
      }, 0);
      setTotal(newTotal);
    };
    calculateTotal();
  }, [cart]);

  useEffect(() => {
    const calculateItemAmount = () => {
      const amount = cart.reduce((accumulator, currentItem) => {
        return accumulator + currentItem.amount;
      }, 0);
      setItemAmount(amount);
    };
    calculateItemAmount();
  }, [cart]);

  const addToCart = (product: IProduct, id: string) => {
    const newItem = { ...product, amount: 1 };
    const cartItem = cart.find((item) => item.id === id);
    if (cartItem) {
      const newCart = cart.map((item) => {
        if (item.id === id) {
          return { ...item, amount: cartItem.amount + 1 };
        } else {
          return item;
        }
      });
      setCart(newCart);
    } else {
      setCart([...cart, newItem]);
    }
  };

  const removeFromCart = (id: string) => {
    const newCart = cart.filter((item) => item.id !== id);
    setCart(newCart);
  };

  const clearCart = () => {
    setCart([]);
  };

  const increaseAmount = (id: string) => {
    const cartItem = cart.find((item) => item.id === id);
    if (cartItem) {
      addToCart(cartItem, id);
    }
  };

  const decreaseAmount = (id: string) => {
    const cartItem = cart.find((item) => item.id === id);
    if (cartItem) {
      const newCart = cart.map((item) => {
        if (item.id === id) {
          return { ...item, amount: cartItem.amount - 1 };
        } else {
          return item;
        }
      });
      setCart(newCart);
    }
    if (cartItem && cartItem.amount < 2) {
      removeFromCart(id);
    }
  };

  return (
    <Context.Provider
      value={{
        cart,
        addToCart,
        removeFromCart,
        clearCart,
        increaseAmount,
        decreaseAmount,
        itemAmount,
        total,
      }}
    >
      {children}
    </Context.Provider>
  );
};

const CartContext = {
  Provider: CartProvider,
  useContext: () => {
    const ctx = React.useContext(Context)
    if (!ctx) throw new Error("useCartContext must be used within a CartContextProvider")
    return ctx;
  },
}
export default CartContext;