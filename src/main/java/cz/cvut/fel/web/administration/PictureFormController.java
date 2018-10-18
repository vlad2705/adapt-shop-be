package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.PictureService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.dto.PictureDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.NativeUploadedFile;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller("pictureForm")
@Scope("session")
@ELBeanName(value = "pictureForm")
@Join(path = "/admin/pictures", to = "/shop/picture-form.jsf")
public class PictureFormController extends AbstractController {

    private final PictureService pictureService;
    private final ProductService productService;

    @ManagedProperty(value = "#{param.id}")
    private Long id;

    private List<PictureDto> pictures;
    private PictureDto selectedPicture;

    @Autowired
    public PictureFormController(PictureService pictureService, ProductService productService) {
        this.pictureService = pictureService;
        this.productService = productService;
    }

    public void initialize(String id) {
        pictures = pictureService.getByProduct(id);
        selectedPicture = pictureService.getPrimaryPicture(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PictureDto> getPictures() {
        return pictures;
    }

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        String id = context.getExternalContext().getRequestParameterMap().get("id");
        PictureDto pictureDto = pictureService.getById(id);
        return new DefaultStreamedContent(new ByteArrayInputStream(pictureDto.getContent()));
    }
    
    public PictureDto getSelectedPicture() {
        return selectedPicture;
    }
    
    public void setSelectedPicture(PictureDto selectedPicture) {
        this.selectedPicture = selectedPicture;
        productService.addPrimaryPicture(getId(), selectedPicture.getId());
    }
    
    public void addPictures(FileUploadEvent event) {
        pictureService.create(getId(), (NativeUploadedFile) event.getFile());
    }

    public void deletePicture(PictureDto picture) {
        pictureService.delete(picture.getId());
        pictures.remove(picture);
    }
    
    public void onRowSelect(SelectEvent event) {
        setSelectedPicture((PictureDto) event.getObject());
    }
}
