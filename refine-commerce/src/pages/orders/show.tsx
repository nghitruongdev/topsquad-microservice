import React from "react";
import {
  IResourceComponentsProps,
  useShow,
  useTranslate,
} from "@refinedev/core";
import { Show, TagField, TextField } from "@refinedev/antd";
import { Table, Typography } from "antd";
import { useKeycloak } from "@react-keycloak/web";

const { Title } = Typography;

export const OrderShow: React.FC<IResourceComponentsProps> = () => {
  const translate = useTranslate();
  const { keycloak } = useKeycloak()
  const { queryResult } = useShow({
    meta: {
      headers: {
        Authorization: `Bearer ${keycloak.token}`
      }
    }
  });

  const { data, isLoading } = queryResult;

  const record = data?.data;
  const columns = [
    {
      title: 'SKU Code',
      dataIndex: 'skuCode',
      key: 'skuCode',
    },
    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      render: (text: string, record: any) => <TextField value={`$${record?.price}`} />,
    },
    {
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
    },
    {
      title: 'SubTotal',
      key: new Date().getTime() * Math.random(),
      render: (text: string, record: any) => <TextField value={`$${record?.quantity * record?.price}`} />,
    },
  ]
  return (
    <Show isLoading={isLoading}>
      <Title level={5}>{translate("orders.fields.orderNumber", "Order Number")}</Title>
      <TextField value={record?.orderNumber} />

      <Title level={5}>{translate("orders.fields.userId", "Username")}</Title>
      <TextField value={record?.userId} />

      <Title level={5}>{translate("orders.fields.orderNumber", "Order Item")}</Title>
      <Table dataSource={record?.items} columns={columns} />
    </Show>
  );
};
