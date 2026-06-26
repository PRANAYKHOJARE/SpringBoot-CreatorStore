let allProducts = [];
let orderItemCount = 0;

function getBase() {
  return "https://springboot-creatorstore-production.up.railway.app";
}

function showToast(msg, type = 'success') {
  const t = document.getElementById('toast');
  t.className = `toast show toast-${type}`;
  t.innerHTML = `<i class="ti ti-${type === 'success' ? 'check' : 'alert-circle'}"></i> ${msg}`;
  setTimeout(() => { t.className = 'toast'; }, 3500);
}

function showPage(page, el) {

    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

    document.getElementById('page-' + page).classList.add('active');
    el.classList.add('active');

    const titles = {
        dashboard:['Dashboard','Overview of your store'],
        products:['Products','Add, edit, and delete products'],
        orders:['Place order','Create a new customer order'],
        history:['Order history','All past customer orders']
    };

    document.getElementById('page-title').textContent=titles[page][0];
    document.getElementById('page-sub').textContent=titles[page][1];

    if(page==="dashboard") loadDashboard();
    if(page==="products") loadProducts();
    if(page==="orders") initOrderPage();
    if(page==="history") loadOrderHistory();

    if(window.innerWidth<=900){
        document.querySelector(".sidebar").classList.remove("active");
    }
}
async function fetchProducts() {
  const r = await fetch(getBase() + '/api/products');
  if (!r.ok) throw new Error('Failed');
  return r.json();
}

async function loadDashboard() {
  try {
    const products = await fetchProducts();
    allProducts = products;
    document.getElementById('stat-total').textContent   = products.length;
    document.getElementById('stat-instock').textContent = products.filter(p => p.stockQuantity > 0).length;
    document.getElementById('stat-oos').textContent     = products.filter(p => p.stockQuantity <= 0).length;
    const tbody = document.getElementById('dash-table');
    if (!products.length) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">No products yet. Add one in the Products tab.</td></tr>';
      return;
    }
    tbody.innerHTML = products.map(p => `
      <tr>
        <td style="font-weight:600">${p.name}</td>
        <td style="color:#a0a09e">${p.category || '—'}</td>
        <td>₹${Number(p.price).toFixed(2)}</td>
        <td>${p.stockQuantity}</td>
        <td><span class="badge ${p.stockQuantity > 0 ? 'badge-success' : 'badge-danger'}">${p.stockQuantity > 0 ? 'In stock' : 'Out of stock'}</span></td>
      </tr>`).join('');
  } catch (e) {
    document.getElementById('dash-table').innerHTML =
      '<tr><td colspan="5" class="empty">Could not connect. Make sure Spring Boot is running and the API URL is correct.</td></tr>';
  }
}

async function loadProducts() {
  try {
    const products = await fetchProducts();
    allProducts = products;
    const tbody = document.getElementById('products-table');
    if (!products.length) {
      tbody.innerHTML = '<tr><td colspan="6" class="empty">No products yet.</td></tr>';
      return;
    }
    tbody.innerHTML = products.map(p => `
      <tr>
        <td style="color:#a0a09e">#${p.id}</td>
        <td style="font-weight:600">${p.name}</td>
        <td style="color:#a0a09e">${p.category || '—'}</td>
        <td>₹${Number(p.price).toFixed(2)}</td>
        <td><span class="badge ${p.stockQuantity > 0 ? 'badge-success' : 'badge-danger'}">${p.stockQuantity}</span></td>
        <td><div class="action-row">
          <button class="btn btn-sm" onclick='editProduct(${JSON.stringify(p)})'>
            <i class="ti ti-edit"></i> Edit
          </button>
          <button class="btn btn-sm btn-danger" onclick="deleteProduct(${p.id}, '${p.name.replace(/'/g,"\\'")}')">
            <i class="ti ti-trash"></i> Delete
          </button>
        </div></td>
      </tr>`).join('');
  } catch (e) {
    document.getElementById('products-table').innerHTML =
      '<tr><td colspan="6" class="empty">Could not connect. Check your API URL.</td></tr>';
  }
}

function editProduct(p) {
  document.getElementById('edit-id').value    = p.id;
  document.getElementById('p-name').value     = p.name;
  document.getElementById('p-cat').value      = p.category || '';
  document.getElementById('p-desc').value     = p.description || '';
  document.getElementById('p-price').value    = p.price;
  document.getElementById('p-stock').value    = p.stockQuantity;
  document.getElementById('form-title').textContent   = 'Edit product #' + p.id;
  document.getElementById('submit-label').textContent = 'Save changes';
  document.getElementById('cancel-btn').style.display = 'inline-flex';
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

function cancelEdit() {
  document.getElementById('edit-id').value    = '';
  document.getElementById('p-name').value     = '';
  document.getElementById('p-cat').value      = '';
  document.getElementById('p-desc').value     = '';
  document.getElementById('p-price').value    = '';
  document.getElementById('p-stock').value    = '';
  document.getElementById('form-title').textContent   = 'Add a product';
  document.getElementById('submit-label').textContent = 'Add product';
  document.getElementById('cancel-btn').style.display = 'none';
}

async function submitProduct() {
  const name  = document.getElementById('p-name').value.trim();
  const price = parseFloat(document.getElementById('p-price').value);
  const stock = parseInt(document.getElementById('p-stock').value);
  if (!name)                    { showToast('Product name is required', 'error'); return; }
  if (!price || price <= 0)     { showToast('Price must be greater than 0', 'error'); return; }
  if (isNaN(stock) || stock < 0){ showToast('Stock quantity is required', 'error'); return; }
  const body = {
    name,
    description:   document.getElementById('p-desc').value,
    category:      document.getElementById('p-cat').value,
    price,
    stockQuantity: stock
  };
  const editId = document.getElementById('edit-id').value;
  try {
    const url    = editId ? `${getBase()}/api/products/${editId}` : `${getBase()}/api/products`;
    const method = editId ? 'PUT' : 'POST';
    const r = await fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
    if (!r.ok) { showToast('Server error: ' + r.status, 'error'); return; }
    showToast(editId ? 'Product updated' : 'Product added');
    cancelEdit();
    loadProducts();
  } catch (e) { showToast('Could not connect to backend', 'error'); }
}

async function deleteProduct(id, name) {
  if (!confirm(`Delete "${name}"? This cannot be undone.`)) return;
  try {
    const r = await fetch(`${getBase()}/api/products/${id}`, { method: 'DELETE' });
    if (!r.ok) { showToast('Delete failed', 'error'); return; }
    showToast('Product deleted');
    loadProducts();
  } catch (e) { showToast('Could not connect to backend', 'error'); }
}

function initOrderPage() {
  fetchProducts().then(p => { allProducts = p; rebuildOrderItems(); }).catch(() => rebuildOrderItems());
}

function rebuildOrderItems() {
  document.getElementById('order-items').innerHTML = '';
  orderItemCount = 0;
  addOrderItem();
}

function addOrderItem() {
  orderItemCount++;
  const id  = orderItemCount;
  const div = document.createElement('div');
  div.className = 'order-item-row';
  div.id = 'oi-row-' + id;
  div.innerHTML = `
    <select id="oi-pid-${id}" onchange="calcTotal()">
      <option value="">Select a product</option>
      ${allProducts.map(p =>
        `<option value="${p.id}" data-price="${p.price}" data-stock="${p.stockQuantity}">
          ${p.name} — ₹${Number(p.price).toFixed(2)} (${p.stockQuantity} left)
        </option>`
      ).join('')}
    </select>
    <input type="number" id="oi-qty-${id}" value="1" min="1" step="1" oninput="calcTotal()" />
    <button class="btn btn-sm btn-danger" onclick="removeOrderItem(${id})" aria-label="Remove">
      <i class="ti ti-x"></i>
    </button>`;
  document.getElementById('order-items').appendChild(div);
}

function removeOrderItem(id) {
  const el = document.getElementById('oi-row-' + id);
  if (el) el.remove();
  calcTotal();
}

function calcTotal() {
  let total = 0;
  document.querySelectorAll('[id^="oi-pid-"]').forEach(sel => {
    const opt = sel.options[sel.selectedIndex];
    if (!opt || !opt.value) return;
    const price = parseFloat(opt.dataset.price) || 0;
    const n     = sel.id.replace('oi-pid-', '');
    const qty   = parseInt(document.getElementById('oi-qty-' + n)?.value) || 0;
    total += price * qty;
  });
  document.getElementById('order-total').textContent = '₹' + total.toFixed(2);
}

async function submitOrder() {
  const name  = document.getElementById('o-name').value.trim();
  const email = document.getElementById('o-email').value.trim();
  if (!name)  { showToast('Customer name is required', 'error'); return; }
  if (!email || !/^[^@]+@[^@]+\.[^@]+$/.test(email)) { showToast('Enter a valid email', 'error'); return; }
  const items = [];
  let valid = true;
  document.querySelectorAll('[id^="oi-pid-"]').forEach(sel => {
    if (!sel.value) return;
    const n     = sel.id.replace('oi-pid-', '');
    const qty   = parseInt(document.getElementById('oi-qty-' + n)?.value) || 0;
    const opt   = sel.options[sel.selectedIndex];
    const stock = parseInt(opt?.dataset.stock) || 0;
    const pname = opt?.text.split(' —')[0] || '';
    if (qty < 1)     { showToast('Quantity must be at least 1', 'error'); valid = false; return; }
    if (qty > stock) { showToast(`Only ${stock} left for "${pname}"`, 'error'); valid = false; return; }
    items.push({ productId: parseInt(sel.value), quantity: qty });
  });
  if (!valid) return;
  if (!items.length) { showToast('Add at least one item', 'error'); return; }
  try {
    const r = await fetch(`${getBase()}/api/orders`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ customerName: name, customerEmail: email, items })
    });
    if (!r.ok) {
      const err = await r.json().catch(() => ({ message: 'Server error' }));
      showToast(err.message || 'Order failed', 'error');
      return;
    }
    const order = await r.json();
    showToast('Order placed — #' + order.id);
    document.getElementById('order-result').style.display = 'block';
    document.getElementById('order-result-body').innerHTML = `
      <div class="result-grid">
        <div class="result-item"><span>Order ID</span><strong>#${order.id}</strong></div>
        <div class="result-item"><span>Status</span><span class="badge badge-success">${order.status}</span></div>
        <div class="result-item"><span>Customer</span><strong>${order.customerName}</strong></div>
        <div class="result-item"><span>Total</span><strong>₹${Number(order.totalPrice).toFixed(2)}</strong></div>
      </div>
      <div style="font-size:12px;color:#a0a09e">Email: ${order.customerEmail}</div>`;
    document.getElementById('o-name').value  = '';
    document.getElementById('o-email').value = '';
    rebuildOrderItems();
    fetchProducts().then(p => { allProducts = p; });
  } catch (e) { showToast('Could not connect to backend', 'error'); }
}

function clearOrder() {
  document.getElementById('o-name').value  = '';
  document.getElementById('o-email').value = '';
  document.getElementById('order-result').style.display = 'none';
  rebuildOrderItems();
}

// ✅ ADDED - Order history
async function loadOrderHistory() {
  try {
    const r = await fetch(getBase() + '/api/orders');
    if (!r.ok) throw new Error('Failed');
    const orders = await r.json();
    const tbody = document.getElementById('history-table');
    if (!orders.length) {
      tbody.innerHTML = '<tr><td colspan="7" class="empty">No orders yet.</td></tr>';
      return;
    }
    tbody.innerHTML = orders.map(o => `
      <tr>
        <td style="color:#a0a09e">#${o.id}</td>
        <td style="font-weight:600">${o.customerName}</td>
        <td style="color:#a0a09e">${o.customerEmail}</td>
        <td>${o.orderItems.map(i =>
          `<div style="font-size:12px">${i.product.name} × ${i.quantity}</div>`
        ).join('')}</td>
        <td>₹${Number(o.totalPrice).toFixed(2)}</td>
        <td>
          <select class="status-select" onchange="updateOrderStatus(${o.id}, this.value)"
            style="font-size:12px;padding:4px 8px;border-radius:6px;border:1px solid #d4d4d2;cursor:pointer;
            color:${statusColor(o.status)}">
            ${['CONFIRMED','PROCESSING','SHIPPED','DELIVERED','CANCELLED'].map(s =>
              `<option value="${s}" ${o.status === s ? 'selected' : ''}>${s}</option>`
            ).join('')}
          </select>
        </td>
        <td style="color:#a0a09e;font-size:12px">${new Date(o.createdAt).toLocaleDateString('en-IN', {
          day: '2-digit', month: 'short', year: 'numeric'
        })}</td>
      </tr>`).join('');
  } catch (e) {
    document.getElementById('history-table').innerHTML =
      '<tr><td colspan="7" class="empty">Could not load orders. Check your connection.</td></tr>';
  }
}

function statusColor(status) {
  const colors = {
    CONFIRMED:  '#1a6ef5',
    PROCESSING: '#a16207',
    SHIPPED:    '#6b21a8',
    DELIVERED:  '#1a7a4a',
    CANCELLED:  '#c0392b'
  };
  return colors[status] || '#1c1c1a';
}

async function updateOrderStatus(id, status) {
  try {
    const r = await fetch(`${getBase()}/api/orders/${id}/status`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ status })
    });
    if (!r.ok) { showToast('Failed to update status', 'error'); return; }
    showToast(`Order #${id} → ${status}`);
  } catch (e) {
    showToast('Could not connect to backend', 'error');
  }
}

// Init
loadDashboard();

function toggleSidebar(){
    document.querySelector(".sidebar").classList.toggle("active");
}

window.addEventListener("resize",function(){

    if(window.innerWidth>900){
        document.querySelector(".sidebar").classList.remove("active");
    }

});