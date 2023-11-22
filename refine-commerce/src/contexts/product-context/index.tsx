import React, { createContext, ReactNode } from "react";
import { IProduct } from "../../types";
import { useList } from "@refinedev/core";

interface ProductContextProps {
  products: IProduct[];
}

const Context = createContext<ProductContextProps | null>(null);

interface ProductProviderProps {
  children: ReactNode;
}

const ProductProvider: React.FC<ProductProviderProps> = ({ children }) => {
  const { data: { data: products } = {} } = useList<IProduct>({
    resource: "products",
    pagination: {
      pageSize: 100
    }
  })
  return (
    <Context.Provider value={{ products: products ?? [] }}>
      {children}
    </Context.Provider>
  );
};

const ProductContext = {
  Provider: ProductProvider,
  useContext: () => {
    const ctx = React.useContext(Context)
    if (!ctx) throw new Error("useProductContext must be used within a ProductContextProvider")
    return ctx;
  }
}
export default ProductContext;
