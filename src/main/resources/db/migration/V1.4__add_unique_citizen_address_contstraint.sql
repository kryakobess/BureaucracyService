alter table citizen_address
    add constraint unique_citizen_address unique (citizen_id, address_id);