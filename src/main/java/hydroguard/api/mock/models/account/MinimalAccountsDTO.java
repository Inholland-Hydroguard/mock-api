package hydroguard.api.mock.models.account;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class MinimalAccountsDTO extends GetMoreDTO<MinimalAccountDTO> {
    public MinimalAccountsDTO() {
    }

    public MinimalAccountsDTO(List<MinimalAccountDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
