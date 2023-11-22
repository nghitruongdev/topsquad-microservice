import React from "react";
import { IResourceComponentsProps, useTranslate } from "@refinedev/core";
import { Edit, useForm } from "@refinedev/antd";
import { Form, Input } from "antd";
import { useKeycloak } from "@react-keycloak/web";

export const InventoryEdit: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { keycloak } = useKeycloak()
  const { formProps, saveButtonProps, queryResult } = useForm({
    meta: {
      headers: {
        Authorization: `Bearer ${keycloak.token}`
      }
    }
  });

  const inventoriesData = queryResult?.data?.data;

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label={translate("inventories.fields.id", "ID")}
          name={["id"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input readOnly disabled />
        </Form.Item>
        <Form.Item
          label={translate("inventories.fields.skuCode", "SKU Code")}
          name={["skuCode"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
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
    </Edit>
  );
};
