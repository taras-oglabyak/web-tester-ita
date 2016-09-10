$(function() {

  $('.select2-multiple').select2({
    theme: 'bootstrap',
    width: '100%'
  });
  
  $('.multiSelect').multiSelect({
    dblClick: true,
    keepOrder: true,
    selectableFooter: "<div id='select-all' class='btn btn-default'>Select All</div>",
    selectionFooter: "<div id='deselect-all' class='btn btn-default'>Deselect all</div>"
  });

  $('#select-all').click(function(){
      $('.multiSelect').multiSelect('select_all');
      return false;
  });
  
  $('#deselect-all').click(function(){
    $('.multiSelect').multiSelect('deselect_all');
    return false;
  });
  
  $('#reset').click(function() {
    location.reload();
    return false;
  });
  
  $('#clean').click(function(e) {
    $('.multipleSelect').select2('val', null);
    $('.multiSelect').multiSelect('deselect_all');
    $('input[type=text],textarea').val('');
    return false;
  });
})    
