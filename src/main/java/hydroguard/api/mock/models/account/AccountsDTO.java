package hydroguard.api.mock.models.account;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class AccountsDTO extends GetMoreDTO<AccountDTO> {
    public AccountsDTO() {
    }

    public AccountsDTO(List<AccountDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}