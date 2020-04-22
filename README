
-----------------------------------------------------------------------------------------------------------------
Preparation of Host Machine
    sudo apt-get update
    sudo apt-get upgrade
    sudo apt-get dist-upgrade
    sudo apt-get install gawk wget git-core diffstat unzip build-essential chrpath libsdl1.2-dev xterm curl
    sudo apt-get install texinfo
    sudo apt-get install lzop
    sudo apt-get install nfs-kernel-server
    sudo apt-get install gcc-arm-linux-gnueabihf
    sudo apt-get install repo
    export CROSS_COMPILE=arm-linux-gnueabihf-
    export ARCH=arm
    sudo apt-get install bison flex
    sudo apt-get install xinetd tftpd tftp

    Modify /etc/xinetd.d/tftp
        service tftp
        {
            protocol        = udp
            port            = 69
            socket_type     = dgram
            wait            = yes
            user            = nobody
            server          = /usr/sbin/in.tftpd
            server_args     = /tftp
            disable         = no
        }
    sudo mkdir /tftp
    sudo chmod -R 777 /tftp
    sudo chown -R nobody /tftp
    mkdir /tftp/imx6
    sudo /etc/init.d/xinetd restart
    sudo reboot

---------------------------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------------------------
Cloning Sourcecode
    # Activities from home dir select accordingly
    cd
    mkdir CaistealOS-MKII
    cd CaistealOS-MKII
    git config --global user.email "summertanks@gmail.com"
    git config --global user.name "Harkirat S Virk"

    # latest being zeus - may change accordingly
    repo init -u https://github.com/Freescale/fsl-community-bsp-platform -b zeus
    repo sync
    MACHINE=imx6ulevk DISTRO=poky source setup-environment build

    # using multiple threads for compilation after the last line
    Modify build/conf/bblayers.conf to add BB_NUMBER_THREADS = "8"

    cd build
    bitbake core-image-minimal

--------------------------------------------------------------------------------------------------------------
Create own repo
    # remove original .git - the exact folder names may change
    cd ~/CaistealOS-MKII/build/tmp/work/imx6ulevk-poky-linux-gnueabi/linux-fslc-imx/4.9-1.0.x+gitAUTOINC+953c6e30c9-r0/git
    rm -rf .git

    Modify .gitignore to add /.pc/* after the last line

    # creating new repo
    git init
    git add .
    git commit -m "Initial Vesion"

    # change the git name as required
    git remote add origin https://github.com/summertanks/CaistealMKII-linux-fslc.git
    git push -u origin master

    # change branch to Zeus, maintain master as it is
    git branch zeus
    git push origin zeus

----------------------------------------------------------------------------------------------------------------
Modified Files

	D sources/caisteal/
	F sources/caisteal/EULA
	D sources/caisteal/conf/
	F sources/caisteal/conf/layer.conf
	D sources/caisteal/conf/machine/
	F sources/caisteal/conf/machine/imx6caisteal.conf
	D sources/caisteal/conf/machine/include/
	F sources/caisteal/conf/machine/include/imx-base.inc
	F sources/caisteal/conf/machine/include/fsl-default-settings.inc 
	F sources/caisteal/conf/machine/include/fsl-default-versions.inc 
	F sources/caisteal/conf/machine/include/utilities.inc
	D sources/caisteal/recipes-bsp/
	D sources/caisteal/recipes-bsp/u-boot/
	F sources/caisteal/recipes-bsp/u-boot/u-boot-caisteal_2019.07.bb
	D sources/caisteal/recipes-kernel/
	D sources/caisteal/recipes-kernel/linux/
	F sources/caisteal/recipes-kernel/linux/linux-caisteal_4.9.bb
	D sources/caisteal/recipes-kernel/linux/linux-caisteal-4.9/
	F sources/caisteal/recipes-kernel/linux/linux-caisteal-4.9/defconfig
	F sources/caisteal/recipes-kernel/linux/linux-caisteal-4.9/0001-Backport-minimal-compiler_attributes.h-to-support-GC.patch
	F sources/caisteal/recipes-kernel/linux/linux-caisteal-4.9/0001-menuconfig-mconf-cfg-Allow-specification-of-ncurses-.patch
	F sources/caisteal/recipes-kernel/linux/linux-caisteal-4.9/0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch
	F build/conf/bblayers.conf
	F 

----------------------------------------------------------------------------------------------------------------

Create Layer
	~/Caisteal/sources$ bitbake-layers create-layer caisteal

Modify build/conf/bblayers.conf and add line
	${BSPDIR}/sources/caisteal \

Duplicate machine conf file
	mkdir caisteal/conf/machine
	cp meta-freescale/conf/machine/imx6ulevk.conf caisteal/conf/machine/imx6ul-caisteal.conf

Modify imx6ul-caisteal.conf
	KERNEL_DEVICETREE = "imx6ul-caisteal.dtb"
	KERNEL_DEVICETREE_use-mainline-bsp = "imx6ul-caisteal.dtb"

	PREFERRED_PROVIDER_u-boot_mx6 = "u-boot-caisteal"
	PREFERRED_PROVIDER_virtual/kernel_mx6 = "linux-caisteal"
	PREFERRED_VERSION_linux-caisteal ?= "4.9"
	
	UBOOT_CONFIG ??= "sd"
	#UBOOT_CONFIG[sd] = "mx6ul_14x14_evk_config,sdcard"
	#UBOOT_CONFIG[emmc] = "mx6ul_14x14_evk_emmc_config,sdcard"
	UBOOT_CONFIG[qspi1] = "mx6ul_caisteal_qspi1_config"
	UBOOT_CONFIG[mfgtool] = "mx6ul_caisteal_config"

Add files for source/caisteal/conf/include directory

	mkdir caisteal/conf/machine/include/
	cp meta-freescale/conf/machine/include/fsl-default-* caisteal/conf/machine/include/
	cp meta-freescale/conf/machine/include/fsl-default-* caisteal/conf/machine/include/
	cp meta-freescale/conf/machine/include/imx-base.inc caisteal/conf/machine/include/
	cp meta-freescale/conf/machine/include/utilities.inc caisteal/conf/machine/include/

Add to source/caisteal/conf/layers.conf
	FSL_EULA_FILE = "${LAYERDIR}/EULA"

	IMX_MIRROR ?= "https://www.nxp.com/lgfiles/NMG/MAD/YOCTO/"
	QORIQ_MIRROR ?= "http://git.freescale.com/source/"
	
	# FIXME: set this to avoid changing all the recipes that use it
	FSL_MIRROR ?= "${IMX_MIRROR}"
	
	MIRRORS += " \
	${IMX_MIRROR}   http://download.ossystems.com.br/bsp/freescale/source/ \n \
	${QORIQ_MIRROR} http://download.ossystems.com.br/bsp/freescale/source/ \n \
	"

	BBFILES_DYNAMIC += " \
	    aglprofilegraphical:${LAYERDIR}/dynamic-layers/aglprofilegraphical/*/*/*.bb \
	    aglprofilegraphical:${LAYERDIR}/dynamic-layers/aglprofilegraphical/*/*/*.bbappend \
	    \
	    browser-layer:${LAYERDIR}/dynamic-layers/browser-layer/*/*/*.bb \
	    browser-layer:${LAYERDIR}/dynamic-layers/browser-layer/*/*/*.bbappend \
	    \
	    filesystem-layer:${LAYERDIR}/dynamic-layers/filesystem-layer/*/*/*.bb \
	    filesystem-layer:${LAYERDIR}/dynamic-layers/filesystem-layer/*/*/*.bbappend \
	    \
	    ivi:${LAYERDIR}/dynamic-layers/ivi/*/*/*.bb \
	    ivi:${LAYERDIR}/dynamic-layers/ivi/*/*/*.bbappend \
	    \
	    networking-layer:${LAYERDIR}/dynamic-layers/networking-layer/*/*/*.bb \
	    networking-layer:${LAYERDIR}/dynamic-layers/networking-layer/*/*/*.bbappend \
	    \
	    openembedded-layer:${LAYERDIR}/dynamic-layers/openembedded-layer/*/*/*.bb \
	    openembedded-layer:${LAYERDIR}/dynamic-layers/openembedded-layer/*/*/*.bbappend \
	    \
	    qt4-layer:${LAYERDIR}/dynamic-layers/qt4-layer/*/*/*.bb \
	    qt4-layer:${LAYERDIR}/dynamic-layers/qt4-layer/*/*/*.bbappend \
	    \
	    qt5-layer:${LAYERDIR}/dynamic-layers/qt5-layer/*/*/*.bb \
	    qt5-layer:${LAYERDIR}/dynamic-layers/qt5-layer/*/*/*.bbappend \
	    \
	    virtualization-layer:${LAYERDIR}/dynamic-layers/virtualization-layer/*/*/*.bb \
	    virtualization-layer:${LAYERDIR}/dynamic-layers/virtualization-layer/*/*/*.bbappend \
	"

Create Recipes u-boot
	mkdir caisteal/recipes-bsp
	mkdir caisteal/recipes-bsp/u-boot/

Create caisteal/recipes-bsp/u-boot/u-boot-caisteal_2019.07.bb
	require recipes-bsp/u-boot/u-boot.inc
  
	inherit fsl-u-boot-localversion

	LICENSE = "GPLv2+"
	LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

	DEPENDS += "bison-native"
	DEPENDS_append = " bc-native dtc-native"

	# SRCREV is commit number
	SRCREV = "6dc993150145c52a1aac1b45ceb12361f2d589a0"
	SRCBRANCH = "zeus"

	SRC_URI = "git://github.com/summertanks/CaistealMKII-uboot.git;branch=${SRCBRANCH}"

	PV = "v2019.07+git${SRCPV}"
	S = "${WORKDIR}/git"
	
	DESCRIPTION = "u-boot bootloader with Caisteal Support."
	
	PROVIDES += "u-boot"
	
	EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
	                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
	                 HOSTSTRIP=true'
	
	PACKAGE_ARCH = "${MACHINE_ARCH}"
	COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|imx6ul-caisteal|use-mainline-bsp)"

Create Recipes Kernel
	mkdir caisteal/recipes-kernel/
	mkdir caisteal/recipes-kernel/linux/
	cp meta-freescale/recipes-kernel/linux/linux-fslc-imx_4.9-1.0.x.bb caisteal/recipes-kernel/linux/linux-caisteal_4.9.bb

Modify linux-caisteal_4.9.bb
	SUMMARY = "FSL Community BSP i.MX Linux kernel with backported features and fixes"
	DESCRIPTION = "Linux kernel based on NXP 4.9.11-1.0.0 GA release with support for Caisteal"

	DEPENDS += "lzop-native bc-native"

	# TODO: Fix Name
	SRC_URI = "git://github.com/summertanks/CaistealMKII-linux-fslc.git;branch=${SRCBRANCH} \
	           file://defconfig"

	LOCALVERSION = "-caisteal"

	PV .= "+git${SRCPV}"

	CVE_VERSION = "${KERNEL_VERSION}"
	# TODO: Change to version
	SRCBRANCH = "zeus"
	
	# Commit number
	SRCREV = "4bb02b19636897a6a0ef2269a24685f6f007843d"
	
	SRC_URI += "file://0001-Backport-minimal-compiler_attributes.h-to-support-GC.patch \
	            file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch \
	           "

	COMPATIBLE_MACHINE = "(mx6|mx7|imx6ul-caisteal)"

Create defconfig
	mkdir caisteal/recipes-kernel/linux/linux-caisteal-4.9
	cp meta-freescale/recipes-kernel/linux/linux-fslc-imx/* caisteal/recipes-kernel/linux/linux-caisteal-4.9/



Create build folder
	MACHINE=imx6ul-caisteal DISTRO=poky source setup-environment build-caisteal

Modify build-caisteal/conf/bblayers.conf and add line
        ${BSPDIR}/sources/caisteal \
