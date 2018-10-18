package cz.cvut.fel.service.impl;

import cz.cvut.fel.asf.adapt.gomawe.AdaptationManager;
import cz.cvut.fel.enums.OrderStatusEnum;
import cz.cvut.fel.model.*;
import cz.cvut.fel.repository.OrderItemRepository;
import cz.cvut.fel.repository.OrderStatusRepository;
import cz.cvut.fel.repository.ProductOrderRepository;
import cz.cvut.fel.repository.ShippingRepository;
import cz.cvut.fel.service.*;
import cz.cvut.fel.utils.RandomIdentifierGenerator;
import cz.cvut.fel.web.client.dto.CartProductDto;
import cz.cvut.fel.web.client.dto.ClientProductOrderDto;
import cz.cvut.fel.web.data.ProductOrderData;
import cz.cvut.fel.web.dto.BaseProductOrderDto;
import cz.cvut.fel.web.dto.ProductOrderDto;
import cz.cvut.fel.web.filter.ProductOrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {
    
    private final ProductOrderRepository productOrderRepository;
    private final PersonService personService;
    private final OrderStatusService orderStatusService;
    private final AccountService accountService;
    private final ShippingRepository shippingRepository;
    private final OrderItemService orderItemService;
    private final ShippingService shippingService;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final RandomIdentifierGenerator randomIdentifierGenerator;
    
    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, PersonService personService, OrderStatusService orderStatusService, AccountService accountService, ShippingRepository shippingRepository, OrderItemService orderItemService, ShippingService shippingService, OrderStatusRepository orderStatusRepository, OrderItemRepository orderItemRepository, RandomIdentifierGenerator randomIdentifierGenerator) {
        this.productOrderRepository = productOrderRepository;
        this.personService = personService;
        this.orderStatusService = orderStatusService;
        this.accountService = accountService;
        this.shippingRepository = shippingRepository;
        this.orderItemService = orderItemService;
        this.shippingService = shippingService;
        this.orderStatusRepository = orderStatusRepository;
        this.orderItemRepository = orderItemRepository;
        this.randomIdentifierGenerator = randomIdentifierGenerator;
    }
    
    @Override
    public BaseProductOrderDto convertToBaseDto(ProductOrder productOrder) {
        if (productOrder != null) {
            BaseProductOrderDto productOrderDto = new BaseProductOrderDto();
            productOrderDto.setId(productOrder.getId());
            productOrderDto.setOrderStatusDto(orderStatusService.convertToDto(productOrder.getOrderStatus()));
            return productOrderDto;
        }
        return null;
    }
    
    @Override
    public ProductOrderDto convertToDto(ProductOrder productOrder) {
        if (productOrder != null) {
            ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setId(productOrder.getId());
            productOrderDto.setOrderStatusDto(orderStatusService.convertToDto(productOrder.getOrderStatus()));
            productOrderDto.setSessionId(productOrder.getSessionId());
            productOrderDto.setAccount(accountService.getBaseDtoByPersonId(productOrder.getPerson().getId()));
            productOrderDto.setPerson(personService.convertToBaseDto(productOrder.getPerson()));
            return productOrderDto;
        }
        return null;
    }
    
    @Override
    public ProductOrder convertToModel(BaseProductOrderDto productOrderDto) {
        if (productOrderDto != null) {
            return productOrderDto.getId() != null
                    ? convertToModel(productOrderRepository.findById(productOrderDto.getId()), productOrderDto)
                    : null;
        }
        return null;
    }
    
    @Override
    public ProductOrder convertToModel(ProductOrder productOrder, BaseProductOrderDto productOrderDto) {
        if (productOrder != null && productOrderDto != null) {
            productOrder.setOrderStatus(orderStatusService.convertToModel(productOrderDto.getOrderStatusDto()));
            return productOrder;
        }
        return null;
    }
    
    @Override
    public BaseProductOrderDto getById(long id) {
        return convertToDto(productOrderRepository.findById(id));
    }
    
    @Override
    public ProductOrderData getDataById(long id) {
        return convertToData(productOrderRepository.findById(id));
    }
    
    @Override
    public BaseProductOrderDto getById(String id) {
        return id == null || id.isEmpty() ? new BaseProductOrderDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public ProductOrderData getDataById(String id) {
        return id == null || id.isEmpty() ? new ProductOrderData() : getDataById(Long.valueOf(id));
    }
    
    @Override
    public ProductOrderData convertToData(ProductOrder productOrder) {
        if (productOrder != null) {
            ProductOrderData productData = new ProductOrderData();
            productData.setId(productOrder.getId());
            productData.setSessionId(productOrder.getSessionId());
            productData.setPersonEmail(productOrder.getPerson().getEmail());
            productData.setOrderStatus(productOrder.getOrderStatus().getName());
            return productData;
        }
        return null;
    }
    
    @Override
    public void changeShipping(Long shippingId) {
        if (shippingId != null) {
            Person person = personService.getAuthorizationPerson();
            List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
            if (productOrders == null || productOrders.isEmpty()) {
                throw new RuntimeException("Bad request!");
            }
            ProductOrder productOrder = productOrders.get(0);
            productOrder.setShipping(shippingRepository.findById(shippingId));
            productOrderRepository.save(productOrder);
        } else {
            throw new RuntimeException("Bad request!");
        }
    }
    
    @Override
    public void acceptOrder() {
        Person person = personService.getAuthorizationPerson();
        List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
        if (productOrders == null || productOrders.isEmpty()) {
            throw new RuntimeException("Bad request!");
        }
        ProductOrder productOrder = productOrders.get(0);
        productOrder.setOrderStatus(orderStatusRepository.findById(OrderStatusEnum.ACCEPTED.getId()));
        productOrderRepository.save(productOrder);
        setPurchase(productOrder);
        incrementPurchaseCount(productOrder);
    }
    
    public void setPurchase(ProductOrder productOrder) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
            List<OrderItem> orderItems = orderItemRepository.findByProductOrder(productOrder.getId());
            orderItems.forEach(orderItem -> model.setPurchaseTime(orderItem.getProduct()));
        }
    }
    
    public void incrementPurchaseCount(ProductOrder productOrder) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            List<OrderItem> orderItems = orderItemRepository.findByProductOrder(productOrder.getId());
            orderItems.forEach(orderItem -> {
                AdaptContentModel model = AdaptationManager.getCurrent().getContentUnitModel(orderItem.getProduct());
                model.incrementPurchaseCount(orderItem.getQuantity());
            });
        }
    }
    
    @Override
    public long getRowCount(ProductOrderFilter filter) {
        return productOrderRepository.findRowCount(filter);
    }
    
    @Override
    public List<ProductOrderData> getByFilter(ProductOrderFilter filter) {
        return productOrderRepository.findByFilter(filter);
    }
    
    @Override
    public ClientProductOrderDto getOrder() {
        Person person = personService.getAuthorizationPerson();
        List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
        if (productOrders == null || productOrders.isEmpty()) {
            throw new RuntimeException("Bad request");
        }
        ProductOrder  productOrder = productOrders.get(0);
        return convertToClientDto(productOrder);
    }
    
    @Override
    public ClientProductOrderDto convertToClientDto(ProductOrder productOrder) {
        if (productOrder != null) {
            ClientProductOrderDto productOrderDto = new ClientProductOrderDto();
            productOrderDto.setId(productOrder.getId());
            productOrderDto.setProducts(orderItemService.getCartByProductOrder(productOrder.getId()));
            productOrderDto.setShipping(shippingService.convertToClientDto(productOrder.getShipping()));
            return productOrderDto;
        }
        return null;
    }
    
    @Override
    public List<CartProductDto> getCart() {
        Person person = personService.getAuthorizationPerson();
        List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
        if (productOrders == null || productOrders.isEmpty()) {
            return Collections.emptyList();
        }
        return orderItemService.getCartByProductOrder(productOrders.get(0).getId());
    }
    
    @Override
    public void changeQuantity(Long productId, Integer quantity) {
        if (productId != null && quantity != null && quantity > 0) {
            Person person = personService.getAuthorizationPerson();
            List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
            if (productOrders == null || productOrders.isEmpty()) {
                throw new RuntimeException("Bad request!");
            }
            orderItemService.changeQuantity(productOrders.get(0).getId(), productId, quantity);
        } else {
            throw new RuntimeException("Bad request!");
        }
    }
    
    @Override
    public long save(long productId) {
        Person person = personService.getAuthorizationPerson();
        List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
        ProductOrder productOrder;
        if (productOrders == null || productOrders.isEmpty()) {
            productOrder = new ProductOrder();
            productOrder.setPerson(person);
            productOrder.setSessionId(randomIdentifierGenerator.generate());
            productOrder.setOrderStatus(orderStatusRepository.findById(OrderStatusEnum.PRE_ORDER.getId()));
            productOrderRepository.save(productOrder);
        } else {
            productOrder = productOrders.get(0);
        }
        return orderItemService.save(productOrder, productId);
    }
    
    @Override
    public BaseProductOrderDto save(BaseProductOrderDto productOrderDto) {
        if (productOrderDto != null) {
            ProductOrder productOrder = convertToModel(productOrderDto);
            productOrderRepository.save(productOrder);
            return convertToDto(productOrder);
        }
        return null;
    }
    
    @Override
    public void deleteProduct(Long productId) {
        if (productId != null) {
            Person person = personService.getAuthorizationPerson();
            List<ProductOrder> productOrders = productOrderRepository.findByPersonAndOrderStatus(person.getId(), OrderStatusEnum.PRE_ORDER.getId());
            if (productOrders == null || productOrders.isEmpty()) {
                throw new RuntimeException("Bad request!");
            }
            orderItemService.deleteProduct(productOrders.get(0).getId(), productId);
        } else {
            throw new RuntimeException("Bad request!");
        }
    }
    
    @Override
    public void delete(long id) {
        productOrderRepository.delete(productOrderRepository.findById(id));
    }
}
