-- Increase the length of host variable tags:

ALTER TABLE "host" ALTER COLUMN "str_tags" TYPE VARCHAR(256);