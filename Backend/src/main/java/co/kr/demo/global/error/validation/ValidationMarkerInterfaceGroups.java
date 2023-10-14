package co.kr.demo.global.error.validation;


import javax.validation.groups.Default;

public class ValidationMarkerInterfaceGroups {
    public interface OnCreateProduct extends Default {}
    public interface OnUpdateProduct extends Default {}

    public interface OnRegisterOrder extends Default {}
}
