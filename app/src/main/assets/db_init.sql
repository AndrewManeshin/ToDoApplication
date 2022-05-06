CREATE TABLE "todo_items" (
	"id"		INTEGER PRIMARY KEY,
	"name"  	TEXT NOT NULL,
	"is_enabled"	INTEGER NOT NULL
);

INSERT INTO "todo_items" ("name", "is_enabled")
VALUES
    ("item_1", 1),
    ("item_2", 1),
    ("item_3", 1),
    ("item_4", 1),
    ("item_5", 1),
    ("item_6", 1),
    ("item_7", 1),
    ("item_8", 1),
    ("item_9", 1),
    ("item_10", 1);