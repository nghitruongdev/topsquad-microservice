import React from "react";
import { createRoot } from "react-dom/client";

import { ReactKeycloakProvider } from "@react-keycloak/web";
import Keycloak from "keycloak-js";

import App from "./App";
import "./i18n";

// const keycloak = new Keycloak({
//   clientId: "refine-demo",
//   url: "https://lemur-0.cloud-iam.com/auth",
//   realm: "refine",
// });
export const keycloak = new Keycloak({
  url: 'http://keycloak:8181/',
  realm: 'spring-react-realm',
  clientId: 'react-client',
})

const container = document.getElementById("root") as HTMLElement;
const root = createRoot(container);

root.render(
  <React.Suspense fallback="loading suspense">
    <ReactKeycloakProvider authClient={keycloak} initOptions={{
      pkceMethod: 'S256',
      onLoad: 'check-sso',
      checkLoginIframe: false,

    }}>
      <App />
    </ReactKeycloakProvider>
  </React.Suspense>
);

// docker run - p 8180: 8180 - e KEYCLOAK_ADMIN = admin - e KEYCLOAK_ADMIN_PASSWORD = admin quay.io / keycloak / keycloak: 22.0.5 start - dev

// docker run --name=keycloak -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev 
