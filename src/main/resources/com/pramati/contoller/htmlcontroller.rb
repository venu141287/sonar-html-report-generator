require 'java'
require '/extensions/plugins/sonar-html-generator.jar'
require '/extensions/plugins/activation-1.1.jar'
require '/extensions/plugins/commons-email-1.2.jar'
require '/extensions/plugins/mail-1.4.1.jar'
class HtmlController < ResourceController
def email
  email = Java::com.pramati.Emailer.new(params[:email],params[:url])
  email.mail()
  end
end