$(function() {

  $.validator.addMethod('regex', function(value, element, regexpr) {
    return regexpr.test(value);
  });

  $('#user').validate({
    onkeyup: function(element) {
      $(element).valid();
    },
    rules: {
      username: {
        required: true,
        remote: {
          url: 'account/isUsernameFree',
          type: 'POST',
          dataType: 'json',
          data: {
            username: function() {
              return $('#username').val();
            },
            id: $('#id').val()
          }
        },
        regex: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
      },
      password: {
        required: true,
        rangelength: [4, 32]
      }
    },
    messages: {
      username: {
        required: 'Email can not be empty',
        remote: 'User with same email already exists',
        regex: 'The value is not a valid email address'
      },
      password: {
        required: 'Password can not be empty',
        rangelength: 'Password length must be between {0} and {1}'
      }
    },
    errorElement: 'em',
    errorPlacement: function(error, element) {
      error.addClass('help-block');
      element.parents('div').addClass('has-feedback');
      error.insertAfter(element);
      if (!element.next('span')[0]) {
        $('<span class="glyphicon glyphicon-remove form-control-feedback"></span>').insertAfter(element);
      }
    },
    success: function(label, element) {
      if (!$(element).next('span')[0]) {
        $('<span class="glyphicon glyphicon-ok form-control-feedback"></span>').insertAfter($(element));
      }
    },
    highlight: function(element, errorClass, validClass) {
      $(element).parent('div').addClass('has-error').removeClass('has-success');
      $(element).next('span').addClass('glyphicon-remove').removeClass('glyphicon-ok');
    },
    unhighlight: function(element, errorClass, validClass) {
      $(element).parent('div').addClass('has-success').removeClass('has-error');
      $(element).next('span').addClass('glyphicon-ok').removeClass('glyphicon-remove');
    },
    submitHandler: function(form) {
      form.submit();
    }
  });

});