package hydroguard.api.mock.models.subscriptions;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class SubscriptionsPlanDTO extends GetMoreDTO<SubscriptionPlanDTO> {
    public SubscriptionsPlanDTO() {}

    public SubscriptionsPlanDTO(List<SubscriptionPlanDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}