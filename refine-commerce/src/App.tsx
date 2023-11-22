import {
  AuthBindings,
  Authenticated,
  GitHubBanner,
  Refine,
} from "@refinedev/core";
import { RefineKbar, RefineKbarProvider } from "@refinedev/kbar";

import {
  ErrorComponent,
  ThemedLayoutV2,
  ThemedSiderV2,
  ThemedTitleV2,
  useNotificationProvider,
} from "@refinedev/antd";
import "@refinedev/antd/dist/reset.css";

import { useKeycloak } from "@react-keycloak/web";
import routerBindings, {
  CatchAllNavigate,
  DocumentTitleHandler,
  NavigateToResource,
  UnsavedChangesNotifier,
} from "@refinedev/react-router-v6";
import dataProvider from './providers/rest-data-provider'
import { App as AntdApp } from "antd";
import axios from "axios";
import { useTranslation } from "react-i18next";
import { BrowserRouter, Navigate, Outlet, Route, Routes } from "react-router-dom";
import { AppIcon } from "./components/app-icon";
import { Header } from "./components/header";
import { ColorModeContextProvider } from "./contexts/color-mode";
import {
  ProductCreate,
  ProductEdit,
  ProductList
} from './pages/products'
import { StoreFrontLayout }
  from "./pages/store-front"
import { Login } from "./pages/auth/login";
import Home from "./pages/store-front/Home";
import ProductDetails from "./pages/store-front/ProductDetails";
import Logout from "./pages/auth/logout";
import { OrderList, OrderShow } from "./pages/orders";
import { InventoryCreate, InventoryEdit, InventoryList } from "./pages/inventories";

function App() {
  const { keycloak, initialized } = useKeycloak();
  const { t, i18n } = useTranslation();

  if (!initialized) {
    return <div>Loading...</div>;
  }

  const authProvider: AuthBindings = {
    login: async (params) => {
      const urlSearchParams = new URLSearchParams(window.location.search);
      const { to } = Object.fromEntries(urlSearchParams.entries());
      await keycloak.login({
        action: params?.action ?? 'login',
        redirectUri: `${window.location.origin}/${to ?? ''}`,
      });
      return {
        success: true,
        redirectTo: to ?? "/",
      };
    },
    logout: async () => {
      try {
        await keycloak.logout({
          redirectUri: window.location.origin,
        });
        return {
          success: true,
          redirectTo: "/login",
        };
      } catch (error) {
        return {
          success: false,
          error: new Error("Logout failed"),
        };
      }
    },
    register: async () => {
      const urlSearchParams = new URLSearchParams(window.location.search);
      const { to } = Object.fromEntries(urlSearchParams.entries());
      keycloak.register({
        redirectUri: `${window.location.origin}/${to ?? ''}`,
      })
      return {
        success: true
      }
    },
    onError: async (error) => {
      console.error(error);
      return { error };
    },
    check: async () => {
      try {
        const { token } = keycloak;
        if (token) {
          axios.defaults.headers.common = {
            Authorization: `Bearer ${token}`,
          };
          return {
            authenticated: true,
          };
        } else {
          return {
            authenticated: false,
            logout: true,
            redirectTo: "/login",
            error: {
              message: "Check failed",
              name: "Token not found",
            },
          };
        }
      } catch (error) {
        return {
          authenticated: false,
          logout: true,
          redirectTo: "/login",
          error: {
            message: "Check failed",
            name: "Token not found",
          },
        };
      }
    },
    getPermissions: async () => null,
    getIdentity: async () => {
      if (keycloak?.tokenParsed) {
        const { preferred_username, email } = keycloak.tokenParsed
        return {
          name: preferred_username ?? email ?? 'Default',
          token: keycloak.token
        };
      }
      return null;
    },
  };

  const i18nProvider = {
    translate: (key: string, params: object) => t(key, params),
    changeLocale: (lang: string) => i18n.changeLanguage(lang),
    getLocale: () => i18n.language,
  };

  return (
    <BrowserRouter>
      {/* <GitHubBanner /> */}
      <RefineKbarProvider>
        <ColorModeContextProvider>
          <AntdApp>
            {/* <DevtoolsProvider> */}
            <Refine
              dataProvider={dataProvider(import.meta.env.VITE_API_URL ?? '')}
              // dataProvider={dataProvider("http://localhost:3000")}
              notificationProvider={useNotificationProvider}
              authProvider={authProvider}
              i18nProvider={i18nProvider}
              routerProvider={routerBindings}
              resources={[
                {
                  name: "products",
                  list: "/admin/products",
                  create: "/admin/products/create",
                  edit: "/admin/products/edit/:id",
                  // show: "/admin/products/show/:id",
                  meta: {
                    canDelete: false,
                  }
                },
                {
                  name: "orders",
                  list: "/admin/orders",
                  show: "/admin/orders/show/:id",
                  meta: {
                    canDelete: false,
                  }
                },
                {
                  name: "inventories",
                  list: "/admin/inventories",
                  create: "/admin/inventories/create",
                  edit: "/admin/inventories/edit/:id",
                  // show: "/admin/products/show/:id",
                  meta: {
                    canDelete: true,
                    headers: {
                      Authorization: `Bearer ${keycloak.token}`
                    }
                  }
                }
              ]}
              options={{
                syncWithLocation: true,
                warnWhenUnsavedChanges: true,
                useNewQueryKeys: true,
                projectId: "uyodmQ-VW4DER-r4Acz0",
              }}
            >
              <Routes>
                <Route element={
                  <StoreFrontLayout>
                    <Outlet />
                  </StoreFrontLayout>
                }>
                  <Route index element={<Home />} />
                  <Route path="/product/:id" element={<ProductDetails />} />
                </Route>
                <Route
                  path="admin"
                  element={
                    <Authenticated
                      key="authenticated-inner"
                      fallback={<CatchAllNavigate to="/login" />}
                    >
                      <ThemedLayoutV2
                        Header={() => <Header sticky />}
                        Sider={(props) => <ThemedSiderV2 {...props} fixed />}
                        Title={({ collapsed }) => (
                          <ThemedTitleV2
                            collapsed={collapsed}
                            text="Squad Commerce"
                            icon={<AppIcon />}
                          />
                        )}
                      >
                        <Outlet />
                      </ThemedLayoutV2>
                    </Authenticated>
                  }
                >
                  <Route index
                    element={<NavigateToResource resource="products" />}
                  />
                  <Route path="products">
                    <Route index element={<ProductList />} />
                    <Route path="create" element={<ProductCreate />} />
                    <Route path="edit/:id" element={<ProductEdit />} />
                    {/* <Route path="show/:id" element={<CategoryShow />} /> */}
                  </Route>

                  <Route path="orders">
                    <Route index element={<OrderList />} />
                    <Route path="show/:id" element={<OrderShow />} />
                  </Route>
                  <Route path="inventories">
                    <Route index element={<InventoryList />} />
                    <Route path="create" element={<InventoryCreate />} />
                    <Route path="edit/:id" element={<InventoryEdit />} />
                    {/* <Route path="show/:id" element={<CategoryShow />} /> */}
                  </Route>
                </Route>


                <Route
                  element={
                    <Authenticated
                      key="authenticated-outer"
                      fallback={<Outlet />}
                    >
                      <Navigate to="/" />
                      {/* <NavigateToResource /> */}
                    </Authenticated>
                  }
                >
                  <Route path="/login" element={<Login />} />
                </Route>
                <Route path="/logout" element={<Logout />} />
                <Route path="*" element={<ErrorComponent />} />
              </Routes>
              <RefineKbar />
              <UnsavedChangesNotifier />
              <DocumentTitleHandler />
            </Refine>
            {/* <DevtoolsPanel /> */}
            {/* </DevtoolsProvider> */}
          </AntdApp>
        </ColorModeContextProvider>
      </RefineKbarProvider>
    </BrowserRouter >
  );
}

export default App;