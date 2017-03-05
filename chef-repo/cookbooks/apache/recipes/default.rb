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
	package = "debian"
end

package 'webserver' do
	package_name package
	action :install
end

service 'webserver' do
	service_name 'webserver'
	action [:start, :enable]
end



