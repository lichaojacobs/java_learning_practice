package com.jacobs.jersey;

import com.jacobs.exception.CommonRestException.CommonExceptionModel;
import com.jacobs.jersey.params.ParamRange;
import com.jacobs.jersey.params.RangeFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lichao on 2017/4/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(
    validatedBy = {ParamDesc.Validator.class, ParamDesc.ListValidator.class}
)
public @interface ParamDesc {

  String message() default "Parameter error!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean isRequired();

  String desc();

  String range() default "";

  public static class ListValidator implements ConstraintValidator<ParamDesc, List<Object>> {

    @Context
    private UriInfo uriInfo;
    @Context
    private HttpServletRequest request;
    private ParamDesc.Validator validator;
    private ParamDesc desc;

    public ListValidator() {
    }

    public void initialize(ParamDesc desc) {
      this.validator = new ParamDesc.Validator(this.uriInfo, this.request);
      this.desc = desc;
      this.validator.initialize(desc);
    }

    public boolean isValid(List<Object> values,
        ConstraintValidatorContext constraintValidatorContext) {
      if (values != null && !values.isEmpty()) {
        boolean isValid1 = true;

        Object o;
        for (Iterator var4 = values.iterator(); var4.hasNext();
            isValid1 &= this.validator.isValid(o, constraintValidatorContext)) {
          o = var4.next();
        }

        return isValid1;
      } else if (this.desc.isRequired()) {
        CommonExceptionModel isValid = CommonExceptionFactor.PARAM_MISSING.getExModel();
        isValid.setRequestUri(this.uriInfo.getAbsolutePath().getPath());
        isValid.setDetailMsg("Parameter {0} missing!");
        this.request.setAttribute("exModel", isValid);
        return false;
      } else {
        return true;
      }
    }
  }

  public static class Validator implements ConstraintValidator<ParamDesc, Object> {

    private static final Logger log = LoggerFactory.getLogger(ParamDesc.Validator.class);
    private UriInfo uriInfo;
    private HttpServletRequest request;
    private ParamDesc desc;
    private ParamRange range;

    public Validator(@Context UriInfo uriInfo, @Context HttpServletRequest request) {
      this.uriInfo = uriInfo;
      this.request = request;
    }

    public void initialize(ParamDesc desc) {
      this.desc = desc;
      this.range = RangeFactory.getRangeInstance(desc.range());
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
      try {
        CommonExceptionModel e;
        if (value == null) {
          if (this.desc.isRequired()) {
            e = CommonExceptionFactor.PARAM_MISSING.getExModel();
            e.setRequestUri(this.uriInfo.getAbsolutePath().getPath());
            e.setDetailMsg("Parameter {0} missing!");
            //将验证错误塞入session中，后面统一抛出
            this.request.setAttribute("exModel", e);
            return false;
          } else {
            return true;
          }
        } else if (this.range != null && !this.range.isInRange(value)) {
          e = CommonExceptionFactor.PARAM_ERROR.getExModel();
          e.setRequestUri(this.uriInfo.getAbsolutePath().getPath());
          e.setDetailMsg("Parameter {0} error! range: " + this.range.getDesc());
          this.request.setAttribute("exModel", e);
          return false;
        } else {
          return true;
        }
      } catch (Exception var5) {
        log.error("valid error", var5);
        CommonExceptionModel exModel = CommonExceptionFactor.PARAM_ERROR.getExModel();
        exModel.setRequestUri(this.uriInfo.getAbsolutePath().getPath());
        exModel.setDetailMsg("Parameter {0} error! range: " + this.range.getDesc());
        this.request.setAttribute("exModel", exModel);
        return false;
      }
    }
  }
}
