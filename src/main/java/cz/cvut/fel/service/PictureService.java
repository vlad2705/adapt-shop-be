package cz.cvut.fel.service;

import cz.cvut.fel.model.Picture;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.web.client.dto.ClientPictureDto;
import cz.cvut.fel.web.dto.PictureDto;
import org.primefaces.model.NativeUploadedFile;

import java.sql.SQLException;
import java.util.List;

public interface PictureService {

    PictureDto convertToDto(NativeUploadedFile file);

    PictureDto convertToDto(Picture picture) throws SQLException;
    
    ClientPictureDto convertToClientDto(Picture picture);

    Picture convertToModel(Product product, PictureDto pictureDto);

    Picture convertToModel(Product product, Picture picture, PictureDto pictureDto);

    Picture convertToModel(Product product, NativeUploadedFile file);

    List<PictureDto> getByProduct(long productId);
    
    PictureDto getPrimaryPicture(String productId);
    
    ClientPictureDto getById(Long pictureId);
    
    List<ClientPictureDto> getClientPicturesByProduct(long productId);

    List<PictureDto> getByProduct(String productId);

    PictureDto getById(String id);

    Picture create(long productId, NativeUploadedFile file);

    void delete(long id);
}
