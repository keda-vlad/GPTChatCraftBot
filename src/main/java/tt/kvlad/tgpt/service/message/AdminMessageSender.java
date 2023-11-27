package tt.kvlad.tgpt.service.message;

import tt.kvlad.tgpt.dto.message.MessageDto;

public interface AdminMessageSender {
    MessageDto makeAdminResponse(Long id, String text);
}
