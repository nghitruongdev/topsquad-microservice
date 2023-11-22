import React from "react";
import {
  IResourceComponentsProps,
  BaseRecord,
  useTranslate,
} from "@refinedev/core";
import { useTable, List, EditButton } from "@refinedev/antd";
import { Table, Space } from "antd";
import { IProduct } from "../../types";

export const ProductList: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { tableProps } = useTable<IProduct>({
    syncWithLocation: true,
  });

  return (
    <List>
      <Table {...tableProps} rowKey="id">
        <Table.Column
          dataIndex="image"
          title={translate("products.fields.img", "Image")}
          render={(value) => <img src={value} alt="Product" height={"50px"} loading="lazy" />}
        />
        <Table.Column
          dataIndex="title"
          title={translate("products.fields.title", "Title")}
        />
        <Table.Column
          dataIndex="category"
          title={translate("products.fields.category", "Category")}
        />
        <Table.Column
          dataIndex="price"
          title={translate("products.fields.price", "Price")}
        />
        <Table.Column
          title={translate("table.actions")}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              <EditButton
                hideText
                size="small"
                recordItemId={record.id}
              />
              {/* <DeleteButton
                hideText
                size="small"
                recordItemId={record.id}
              /> */}
            </Space>
          )}
        />
      </Table>
    </List>
  );
};
