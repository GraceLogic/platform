package com.gracelogic.platform.market.service;

import com.gracelogic.platform.account.dto.CurrencyDTO;
import com.gracelogic.platform.account.exception.AccountNotFoundException;
import com.gracelogic.platform.account.exception.CurrencyMismatchException;
import com.gracelogic.platform.account.exception.InsufficientFundsException;
import com.gracelogic.platform.account.exception.NoActualExchangeRateException;
import com.gracelogic.platform.db.dto.EntityListResponse;
import com.gracelogic.platform.db.exception.ObjectNotFoundException;
import com.gracelogic.platform.market.dto.*;
import com.gracelogic.platform.market.exception.*;
import com.gracelogic.platform.market.model.Discount;
import com.gracelogic.platform.market.model.Order;
import com.gracelogic.platform.market.model.Product;
import com.gracelogic.platform.payment.dto.PaymentExecutionResultDTO;
import com.gracelogic.platform.payment.exception.InvalidPaymentSystemException;
import com.gracelogic.platform.payment.exception.PaymentExecutionException;
import com.gracelogic.platform.payment.model.Payment;
import com.gracelogic.platform.user.dto.AuthorizedUser;
import com.gracelogic.platform.user.exception.ForbiddenException;

import java.util.*;

public interface MarketService {
    Order saveOrder(OrderDTO dto, AuthorizedUser authorizedUser, boolean trust) throws InvalidOrderStateException, OrderNotConsistentException, ObjectNotFoundException, ForbiddenException, InvalidDiscountException, NoActualExchangeRateException, ProductSubscriptionException, EmptyOrderException, InvalidCurrencyException, InvalidProductException;

    PaymentExecutionResultDTO executeOrder(UUID orderId, UUID paymentSystemId, Map<String, String> params, AuthorizedUser authorizedUser, boolean trust) throws InvalidOrderStateException, OrderNotConsistentException, ForbiddenException, InvalidPaymentSystemException, AccountNotFoundException, InsufficientFundsException, InvalidDiscountException, ObjectNotFoundException, PaymentExecutionException, CurrencyMismatchException;

    void cancelOrder(UUID orderId) throws InvalidOrderStateException, ObjectNotFoundException, InsufficientFundsException, AccountNotFoundException, CurrencyMismatchException;

    void processPayment(Payment payment) throws InvalidOrderStateException, AccountNotFoundException, InsufficientFundsException, CurrencyMismatchException;

    void deleteOrder(UUID orderId, AuthorizedUser authorizedUser, boolean trust) throws InvalidOrderStateException, ObjectNotFoundException, ForbiddenException;

    OrderDTO getOrder(UUID id, boolean enrich, boolean withProducts, AuthorizedUser authorizedUser, boolean trust) throws ObjectNotFoundException, ForbiddenException;

    EntityListResponse<OrderDTO> getOrdersPaged(UUID userId, UUID ownerId, UUID orderStateId, UUID discountId, Double totalAmountGreatThan, boolean onlyEmptyParentOrder, boolean enrich, boolean calculate, boolean withProducts, Integer count, Integer page, Integer start, String sortField, String sortDir);

    //Product
    ProductDTO getProduct(UUID id, boolean enrich) throws ObjectNotFoundException;

    EntityListResponse<ProductDTO> getProductsPaged(String name, UUID productTypeId, Boolean active, boolean enrich, boolean calculate, Integer count, Integer page, Integer start, String sortField, String sortDir);

    Product saveProduct(ProductDTO dto) throws ObjectNotFoundException, PrimaryProductException, ProductSubscriptionException;

    void deleteProduct(UUID id) throws ObjectNotFoundException;


    //Discount
    DiscountDTO getDiscount(UUID id, boolean enrich, boolean withProducts) throws ObjectNotFoundException;

    EntityListResponse<DiscountDTO> getDiscountsPaged(String name, UUID usedForOrderId, UUID discountTypeId, boolean enrich, boolean calculate, boolean withProducts, Integer count, Integer page, Integer start, String sortField, String sortDir);

    Discount saveDiscount(DiscountDTO dto) throws ObjectNotFoundException, CurrencyMismatchException;

    void deleteDiscount(UUID id) throws ObjectNotFoundException;


    void checkAtLeastOneProductPurchased(UUID ownerId, Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, Date checkOnDate) throws ProductNotPurchasedException;

    Map<UUID, List<PurchasedProductDTO>> getProductsPurchaseState(UUID ownerId, Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, Date checkDate);

    Map<UUID, List<Product>> findProducts(Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, boolean onlyPrimary);

    void enrichMarketInfo(UUID productTypeId, Collection<MarketAwareObjectDTO> objects, UUID relatedOwnerId, Date checkOnDate, boolean onlyPrimary);


    List<CurrencyDTO> getAvailableCurrencies();


    void queueCashierVoucher(UUID cashierVoucherTypeId, Order order);

    void cancelSubscription(UUID orderId, AuthorizedUser authorizedUser, boolean trust) throws ObjectNotFoundException, ProductSubscriptionException, InvalidOrderStateException, ForbiddenException;
}