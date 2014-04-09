(function($){

var _module = {} ;
eXo.calendar = eXo.calendar || {} ;

function UIColorPicker() {}

/**
 * entry point of color picker, invoked when clicking on the color picker input
 * adapt the popup to overlap the parent div (tab Details)
 * note that we use boopstrap dropdown to display the table color
 */
UIColorPicker.prototype.adaptPopup = function(inputColorPicker) {
  // store children of color picker in property
  this.inputColorPicker = inputColorPicker;
  this.formColorPicker  = $(inputColorPicker).parent('.uiFormColorPicker');
  this.tableColor       = $(this.formColorPicker).children('.calendarTableColor')[0];
  this.valueColorPicker = $(this.formColorPicker).children('input.uiColorPickerValue')[0];

  $(this.formColorPicker).css('position', 'static');  
  var offsetLeft = $(this.formColorPicker).position().left - 20;
  var offsetTop = $(this.formColorPicker).position().top + 20;  
  
  // table color is positioned relative to the tab Details 
  $(this.tableColor).css('position', 'absolute');
  $(this.tableColor).css('left', offsetLeft + 'px');
  $(this.tableColor).css('top', offsetTop + 'px');
};

/**
 * change color of current input 
 * @param a tag with colorCell class
 */
UIColorPicker.prototype.setColor = function(colorCell) {
  var clazz = $(colorCell).attr('class').split(' '); 
  var color = $.trim(clazz[0]); 
  var className = 'displayValue ' + color;
  var spanDisplayedColor = $(this.inputColorPicker).children('span.displayValue')[0];
  spanDisplayedColor.className = className; // change displayed color
  this.valueColorPicker.value  = color; // update the input 

  this.clearSelectedColor(); 
  this.updateNewSelectedColor(); 
};


/**
 * clear the selected cell
 */
UIColorPicker.prototype.clearSelectedColor = function() {  
  $(this.tableColor).find('i').each(function() {
    if ($(this).hasClass('iconCheckBox')) {
      $(this).removeClass('iconCheckBox');
      return false;
    }
  });
};


UIColorPicker.prototype.updateNewSelectedColor = function() {  
  var newColor = this.valueColorPicker.value;

  $(this.tableColor).find('a').each(function() {
    if ($(this).hasClass(newColor)) {
      $(this).children('i').addClass('iconCheckBox');
      return false;
    }
  });
};


_module.UIColorPicker      = new UIColorPicker();
eXo.calendar.UIColorPicker = _module.UIColorPicker;

return _module.UIColorPicker;

}) ($);