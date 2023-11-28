INSERT INTO telegram_chats (id, chat_id, target_context_id, is_deleted) VALUES
(1, 123456789, null, false);

INSERT INTO contexts (id, name, context_type, telegram_chat_id, is_deleted) VALUES
(1, 'Context1', 'ADMIN', 1, false),
(2, 'Context2', 'GPT', 1, false);

INSERT INTO chat_messages (id, text, signature, creation_time, context_id, chat_id, is_deleted) VALUES
(1, 'Message1 for Context1', 'user', '2023-11-27 13:27:25.447832', 1, 1, false),
(2, 'Message2 for Context1', 'user', '2023-11-27 13:27:25.447832', 1, 1, false),
(3, 'Message3 for Context1', 'user', '2023-11-27 13:27:25.447832', 1, 1, false),
(4, 'Message1 for Context2', 'user', '2023-11-27 13:27:25.447832', 2, 1, false),
(5, 'Message2 for Context2', 'user', '2023-11-27 13:27:25.447832', 2, 1, false),
(6, 'Message3 for Context2', 'user', '2023-11-27 13:27:25.447832', 2, 1, false);