import React from "react";
import { IResourceComponentsProps, useGetIdentity, useTranslate } from "@refinedev/core";
import { Create, useForm, getValueFromEvent } from "@refinedev/antd";
import { Form, Input, Upload } from "antd";

export const ProductCreate: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { data: user } = useGetIdentity<{ token: string }>()
  const { formProps, saveButtonProps, queryResult } = useForm({
    meta: {
      headers: {
        Authorization: `Bearer ${user?.token}`
      }
    }
  });

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label={translate("products.fields.title", "Title")}
          name={["title"]}
          rules={[
            {
              required: true,
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
              required: true,
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
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label={translate("products.fields.image", "Image")}
          name={["image"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>

        {/* <Form.Item label={translate("products.fields.image")}>
          <Form.Item
            name="image"
            getValueProps={(value) => ({
              fileList: [{ url: value, name: value, uid: value }],
            })}
            getValueFromEvent={getValueFromEvent}
            noStyle
            rules={[
              {
                required: true,
              },
            ]}
          > */}

        {/* <Upload.Dragger
              listType="picture"
              beforeUpload={() => false}
            >
              <p className="ant-upload-text">
                Drag & drop a file in this area
              </p>
            </Upload.Dragger> */}
        {/* </Form.Item> */}
        {/* </Form.Item> */}
      </Form>
    </Create >
  );
};
