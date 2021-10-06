package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto implements Serializable {

    private Long id;

    private String name;

    private Long size;

    private String contentType;
}
