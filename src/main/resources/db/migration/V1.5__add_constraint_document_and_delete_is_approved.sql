alter table document
    add constraint unique_citizen_document_type unique (citizen_id, document_type);

alter table citizen drop column is_approved;