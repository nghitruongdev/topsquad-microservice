import React from "react";
import {
  IResourceComponentsProps,
  BaseRecord,
  useTranslate,
} from "@refinedev/core";
import { useTable, List, EditButton, DeleteButton } from "@refinedev/antd";
import { Table, Space } from "antd";
import { useKeycloak } from "@react-keycloak/web";
import { keycloak } from '../../index';

export const InventoryList: React.FC<IResourceComponentsProps> = () => {
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
          title={translate("inventories.fields.id", "ID")}
        />
        <Table.Column
          dataIndex="skuCode"
          title={translate("inventories.fields.skuCode", "SKU Code")}
        />
        <Table.Column
          dataIndex="quantity"
          title={translate("inventories.fields.quantity", "Quantity")}
        />
        <Table.Column
          title={translate("table.actions")}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              {/* <EditButton
                hideText
                size="small"
                recordItemId={record.id}
              /> */}

              <DeleteButton
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
