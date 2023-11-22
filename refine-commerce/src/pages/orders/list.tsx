import React from "react";
import {
  BaseRecord,
  IResourceComponentsProps,
  useTranslate,
} from "@refinedev/core";
import { useTable, List, ShowButton } from "@refinedev/antd";
import { Space, Table } from "antd";
import { useKeycloak } from "@react-keycloak/web";

export const OrderList: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { keycloak } = useKeycloak()
  const { tableProps } = useTable({
    syncWithLocation: true,
    meta: {
      headers: {
        Authorization: `Bearer ${keycloak.token}`
      }
    }
  });

  return (
    <List>
      <Table {...tableProps} rowKey="id">
        <Table.Column
          dataIndex="id"
          title={translate("orders.fields.id", "# Order")}
        />
        <Table.Column
          dataIndex="userId"
          title={translate("orders.fields.userId", "Username")}
        />

        <Table.Column
          dataIndex="items"
          title={translate("products.fields.price", "Total")}
          render={(value: ({ price: number, quantity: number })[]) => {
            const total = value.reduce((acc, { price, quantity }) => acc += (price * quantity), 0)
            return <>${total}</>
          }}
        />
        <Table.Column
          title={translate("table.actions")}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              <ShowButton
                hideText
                size="small"
                recordItemId={record.id}
              />
            </Space>
          )}
        />
      </Table>
    </List>
  );
};