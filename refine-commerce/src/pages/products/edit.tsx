import React from "react";
import { IResourceComponentsProps, useGetIdentity, useTranslate } from "@refinedev/core";
import { Edit, useForm, getValueFromEvent } from "@refinedev/antd";
import { Form, Image, Input, Upload } from "antd";
import { useKeycloak } from '@react-keycloak/web';

export const ProductEdit: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { data: user } = useGetIdentity<{ token: string }>()
  const { formProps, saveButtonProps, queryResult } = useForm({
    meta: {
      headers: {
        "Authorization": `Bearer ${user?.token}`
      }
    }
  });

  const productsData = queryResult?.data?.data;

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label={translate("products.fields.id", "ID")}
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
          label={translate("products.fields.title", "Title")}
          name={["title"]}
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label={translate("products.fields.price", "Price")}
          name={["price"]}
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label={translate("products.fields.description", "Description")}
          name="description"
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input.TextArea rows={5} />
        </Form.Item>
        <Form.Item
          label={translate("products.fields.category", "Category")}
          name={["category"]}
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
        {/* <Image src={imageUrl} width={200} /> */}
        <Form.Item
          label={translate("products.fields.image", "Image")}
          name={["image"]}
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Edit>
  );
};
