import { ThemedTitleV2 } from "@refinedev/antd";
import { useLogin, useRegister, useTranslate } from "@refinedev/core";
import { Button, Layout, Space, Typography } from "antd";
import { AppIcon } from "../../components/app-icon";

export const Login: React.FC = () => {
  const { mutate: login } = useLogin();
  const { mutate: register } = useRegister()
  const t = useTranslate();

  return (
    <Layout
      style={{
        height: "100vh",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Space direction="vertical" align="center">
        <ThemedTitleV2
          collapsed={false}
          wrapperStyles={{
            fontSize: "22px",
            marginBottom: "36px",
          }}
          text="Squad Commerce"
          icon={<AppIcon />}
        />
        <Button
          style={{ width: "240px" }}
          type="primary"
          size="middle"
          className="h-12 bg-blue-600 text-white"
          onClick={() => login({})}
        >
          {t("pages.login.signin", "Sign in")}
        </Button>
        <Button
          style={{ width: "240px" }}
          size="middle"
          type="primary"
          className="h-12 bg-blue-600 text-white"
          onClick={() => register({})}
        >
          {t("pages.login.register", "Register new account")}
        </Button>
        <Typography.Text type="secondary" className="flex ">
          Powered by
          <img
            style={{ padding: "0 5px" }}
            alt="Keycloak"
            src="https://refine.ams3.cdn.digitaloceanspaces.com/superplate-auth-icons%2Fkeycloak.svg"
          />
          Keycloak
        </Typography.Text>
      </Space>
    </Layout>
  );
};
