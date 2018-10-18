package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Picture;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.repository.PictureRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.service.PictureService;
import cz.cvut.fel.web.client.dto.ClientPictureDto;
import cz.cvut.fel.web.dto.PictureDto;
import org.primefaces.model.NativeUploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ProductRepository productRepository) {
        this.pictureRepository = pictureRepository;
        this.productRepository = productRepository;
    }

    @Override
    public PictureDto convertToDto(NativeUploadedFile file) {
        if (file != null) {
            PictureDto pictureDto = new PictureDto();
            pictureDto.setName(file.getFileName());
            pictureDto.setContent(file.getContents());
            pictureDto.setType(file.getContentType());
            pictureDto.setSize(file.getSize());
            return pictureDto;
        }
        return null;
    }

    @Override
    public PictureDto convertToDto(Picture picture) {
        if (picture != null) {
            PictureDto pictureDto = new PictureDto();
            pictureDto.setId(picture.getId());
            pictureDto.setName(picture.getName());
            pictureDto.setContent(picture.getContent());
            pictureDto.setType(picture.getType());
            pictureDto.setSize(picture.getSize());
            return pictureDto;
        }
        return null;
    }
    
    @Override
    public ClientPictureDto convertToClientDto(Picture picture) {
        if (picture != null) {
            ClientPictureDto clientPictureDto = new ClientPictureDto();
            clientPictureDto.setContent(picture.getContent());
            clientPictureDto.setType(picture.getType());
            return clientPictureDto;
        }
        return null;
    }
    
    @Override
    public Picture convertToModel(Product product, PictureDto pictureDto) {
        if (product != null && pictureDto != null) {
            return pictureDto.getId() != null
                    ? convertToModel(product, pictureRepository.findById(pictureDto.getId()), pictureDto)
                    : convertToModel(product, new Picture(), pictureDto);
        }
        return null;
    }

    @Override
    public Picture convertToModel(Product product, Picture picture, PictureDto pictureDto) {
        if (product != null && picture != null && pictureDto != null) {
            picture.setProduct(product);
            picture.setName(pictureDto.getName());
            picture.setContent(pictureDto.getContent());
            picture.setType(pictureDto.getType());
            picture.setSize(pictureDto.getSize());
            return picture;
        }
        return null;
    }

    @Override
    public Picture convertToModel(Product product, NativeUploadedFile file) {
        if (file != null) {
            Picture picture = new Picture();
            picture.setProduct(product);
            picture.setName(file.getFileName());
            picture.setContent(file.getContents());
            picture.setType(file.getContentType());
            picture.setSize(file.getSize());
            return picture;
        }
        return null;
    }

    @Override
    public List<PictureDto> getByProduct(long productId) {
        return pictureRepository.findByProduct(productId).stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    @Override
    public PictureDto getPrimaryPicture(String productId) {
        if (productId != null && !productId.isEmpty()) {
            Product product = productRepository.findById(Long.valueOf(productId));
            return product != null ? convertToDto(product.getPicture()) : null;
        }
        return null;
    }
    
    @Override
    public ClientPictureDto getById(Long pictureId) {
        if (pictureId != null) {
            Picture picture  = pictureRepository.findById(pictureId);
            return convertToClientDto(picture);
        }
        return null;
    }
    
    @Override
    public List<ClientPictureDto> getClientPicturesByProduct(long productId) {
        return pictureRepository.findByProduct(productId).stream().map(this::convertToClientDto).collect(Collectors.toList());
    }
    
    @Override
    public List<PictureDto> getByProduct(String productId) {
        return productId == null || productId.isEmpty() ? new ArrayList<>() : getByProduct(Long.parseLong(productId));
    }

    @Override
    public PictureDto getById(String id) {
        if (id != null && !id.isEmpty()) {
            return convertToDto(pictureRepository.findById(Long.valueOf(id)));
        }
        return null;
    }

    @Override
    public Picture create(long productId, NativeUploadedFile file) {
        Product product = productRepository.findById(productId);
        Picture picture = convertToModel(product, file);
        pictureRepository.save(picture);
        return picture;
    }

    @Override
    public void delete(long id) {
        Picture picture = pictureRepository.findById(id);
        pictureRepository.delete(picture);
    }
}
