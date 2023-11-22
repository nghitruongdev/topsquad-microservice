import { PropsWithChildren } from "react"
import { Footer, Header, Sidebar } from "../../components/store"
import CartContext from "../../contexts/cart-context"
import ProductContext from "../../contexts/product-context"
import SidebarContext from "../../contexts/sidebar-context"

export const StoreFrontLayout = ({ children }: PropsWithChildren) => {
    return (
        <SidebarContext.Provider>
            <CartContext.Provider>
                <ProductContext.Provider>
                    <Header />
                    {children}
                    <Sidebar />
                    <Footer />
                </ProductContext.Provider>
            </CartContext.Provider>
        </SidebarContext.Provider>
    )
}