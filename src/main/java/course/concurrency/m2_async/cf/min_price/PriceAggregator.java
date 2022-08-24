package course.concurrency.m2_async.cf.min_price;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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

        final SortedSet<Double> synchronizedDoubleSet = Collections.synchronizedSortedSet(new TreeSet<>());
        List<CompletableFuture<Double>> actions = shopIds.stream()
            .map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId), executor))
            .peek(it -> it.thenAcceptAsync(synchronizedDoubleSet::add)).collect(Collectors.toList());

        try {
            //Тут не получается задать 3000, т.к. последующая операция занимает определённое кол-во мс :((
            Thread.sleep(2950);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        actions.forEach(it -> it.complete(Double.MAX_VALUE));
        if( synchronizedDoubleSet.isEmpty()) {
            return Double.NaN;
        }
            return synchronizedDoubleSet.first();
    }
}
