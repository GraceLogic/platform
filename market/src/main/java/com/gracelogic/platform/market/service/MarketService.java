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
    Order saveOrder(OrderDTO dto, AuthorizedUser authorizedUser) throws InvalidOrderStateException, OrderNotConsistentException, ObjectNotFoundException, ForbiddenException, InvalidDiscountException, NoActualExchangeRateException, ProductSubscriptionException, EmptyOrderException, InvalidCurrencyException, InvalidProductException;

    PaymentExecutionResultDTO executeOrder(UUID orderId, UUID paymentSystemId, Map<String, String> params, AuthorizedUser authorizedUser) throws InvalidOrderStateException, OrderNotConsistentException, ForbiddenException, InvalidPaymentSystemException, AccountNotFoundException, InsufficientFundsException, InvalidDiscountException, ObjectNotFoundException, PaymentExecutionException, CurrencyMismatchException;

    void cancelOrder(UUID orderId) throws InvalidOrderStateException, ObjectNotFoundException, InsufficientFundsException, AccountNotFoundException, CurrencyMismatchException;

    void processPayment(Payment payment) throws InvalidOrderStateException, AccountNotFoundException, InsufficientFundsException, CurrencyMismatchException;

    void deleteOrder(UUID orderId, AuthorizedUser authorizedUser) throws InvalidOrderStateException, ObjectNotFoundException, ForbiddenException;

    OrderDTO getOrder(UUID id, boolean enrich, boolean withProducts, AuthorizedUser authorizedUser) throws ObjectNotFoundException, ForbiddenException;

    EntityListResponse<OrderDTO> getOrdersPaged(UUID userId, UUID orderStateId, UUID discountId, Double totalAmountGreatThan, boolean onlyEmptyParentOrder, boolean enrich, boolean withProducts, Integer count, Integer page, Integer start, String sortField, String sortDir, boolean calculate);

    //Product
    ProductDTO getProduct(UUID id, boolean enrich) throws ObjectNotFoundException;

    EntityListResponse<ProductDTO> getProductsPaged(String name, UUID productTypeId, Boolean active, boolean enrich, Integer count, Integer page, Integer start, String sortField, String sortDir, boolean calculate);

    Product saveProduct(ProductDTO dto) throws ObjectNotFoundException, PrimaryProductException, ProductSubscriptionException;

    void deleteProduct(UUID id) throws ObjectNotFoundException;


    //Discount
    DiscountDTO getDiscount(UUID id, boolean enrich, boolean withProducts) throws ObjectNotFoundException;

    EntityListResponse<DiscountDTO> getDiscountsPaged(String name, UUID usedForOrderId, UUID discountTypeId, boolean enrich, boolean withProducts, Integer count, Integer page, Integer start, String sortField, String sortDir, boolean calculate);

    Discount saveDiscount(DiscountDTO dto) throws ObjectNotFoundException, CurrencyMismatchException;

    void deleteDiscount(UUID id) throws ObjectNotFoundException;


    void checkAtLeastOneProductPurchased(UUID userId, Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, Date checkOnDate) throws ProductNotPurchasedException;

    Map<UUID, List<PurchasedProductDTO>> getProductsPurchaseState(UUID userId, Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, Date checkDate);

    Map<UUID, List<Product>> findProducts(Map<UUID, UUID> referenceObjectIdsAndProductTypeIds, boolean onlyPrimary);

    void enrichMarketInfo(UUID productTypeId, Collection<MarketAwareObjectDTO> objects, UUID relatedUserId, Date checkOnDate, boolean onlyPrimary);


    List<CurrencyDTO> getAvailableCurrencies();


    void queueCashierVoucher(UUID cashierVoucherTypeId, Order order);
}