#
# Cookbook Name:: apache
# Recipe:: default
#
# Copyright 2017, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
if node['plataform_family'] == 'rhel'
	package = "httpd"
elsif node['plataform_family'] == 'debian'
	package = "apache2"
end

webnodes = search('node','role:web')

webnodes.each do |node|
	puts node

end

package 'webserver' do
	package_name package
	action :install
end

service 'webserver' do
	service_name 'httpd'
	action [:start, :enable]
end



