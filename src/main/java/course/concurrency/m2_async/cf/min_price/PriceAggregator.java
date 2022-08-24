package course.concurrency.m2_async.cf.min_price;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {

        List<Future<Double>> actions = shopIds.stream().map(shopId -> executor.submit(() -> priceRetriever.getPrice(itemId, shopId))).collect(Collectors.toList());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        return actions.stream().filter(it -> it.isDone()).mapToDouble(it -> {
            try {
                return it.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Это никогда не свалится");
            }
        }).min().orElse(0);

    }
}
