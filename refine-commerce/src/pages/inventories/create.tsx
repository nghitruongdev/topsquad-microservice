import React from "react";
import { IResourceComponentsProps, useTranslate } from "@refinedev/core";
import { Create, useForm, useSelect } from "@refinedev/antd";
import { Form, Input, Select } from "antd";
import { useKeycloak } from "@react-keycloak/web";

export const InventoryCreate: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { keycloak } = useKeycloak()
  const { formProps, saveButtonProps, queryResult } = useForm({
    meta: {
      headers: {
        Authorization: `Bearer ${keycloak.token}`
      }

    }
  });
  const { queryResult: { data: products }, selectProps } = useSelect({
    resource: "products"
  })
  return (
    <Create saveButtonProps={saveButtonProps}>

      <Form {...formProps} layout="vertical">
        <Form.Item
          label={translate("inventories.fields.skuCode", "SKU Code")}
          name={["skuCode"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Select
            placeholder="Select a product"
            style={{ width: 300 }}
            {...selectProps}
          />
          {/* <Input /> */}
        </Form.Item>
        <Form.Item
          label={translate("inventories.fields.quantity", "Quantity")}
          name={["quantity"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Create>
  );
};
