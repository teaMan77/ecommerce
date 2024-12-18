package com.example.project.service;

import com.example.project.exceptions.APIException;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.model.CartItem;
import com.example.project.model.Product;
import com.example.project.payload.ProductDTO;
import com.example.project.repository.CartItemRepository;
import com.example.project.repository.ProductRepository;
import com.example.project.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.project.model.Cart;
import com.example.project.payload.CartDTO;
import com.example.project.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        //Find existing cart or create one
        Cart cart = createCart();
        
        //Retrieve product details
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cart.getCartId());

        //Perform Validations
        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0) {
            throw new APIException("Product " + product.getProductName() + " is out of stock");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " +
                    product.getProductName() + " less than or equal to " + product.getQuantity() + ".");
        }

        //Create Cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        //Save Cart Item
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        //Return the updated Cart
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {
                ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
                map.setQuantity(item.getQuantity());
                return map;
                });

        cartDTO.setProducts(productDTOStream.toList());

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()) {
            throw new APIException("No cart exists!!!");
        }

        List<CartDTO> cartDTOS = carts.stream()
                .map(cart -> {CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

                    List<ProductDTO> productDTOS = cart.getCartItems().stream()
                            .map(prod -> modelMapper.map(prod, ProductDTO.class)).toList();

                    cartDTO.setProducts(productDTOS);
                    return cartDTO;
                }).toList();

        return cartDTOS;
    }

    @Override
    public CartDTO getCart(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email, cartId);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(cartItem ->
        {cartItem.getProduct().setQuantity(cartItem.getQuantity());});
        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(prod -> modelMapper.map(prod, ProductDTO.class)).toList();
        cartDTO.setProducts(productDTOS);

        return cartDTO;
    }

    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {

        String emailId = authUtils.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);
        Long cartId  = userCart.getCartId();

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setDiscount(product.getDiscount());
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
        cartRepository.save(cart);
        CartItem updatedItem = cartItemRepository.save(cartItem);
        if(updatedItem.getQuantity() == 0){
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtils.loggedInUser());

        return cartRepository.save(cart);
    }

}
